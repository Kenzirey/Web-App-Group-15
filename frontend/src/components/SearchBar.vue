<template>
	<v-form ref="searchBar" @submit.prevent="submitSearch(null, false)">
		<button style="display: none;"></button>
		<!-- Prevents other buttons from being invoked as the submit when clicking enter -->
		<v-text-field @keydown.tab="showSuggestions=false" id="search-bar" @click="searchBarFocused" @focus="searchBarFocused" v-model="search" append-icon="mdi-magnify"
			@click:append="submitSearch(null, false)" placeholder="Search..." required autocomplete="off"
			hide-details />
		<div id="search-suggestions-center-align">
			<div v-if="showSuggestions" id="search-suggestions">
				<div id="courses-suggestions" class="suggestion-box">
					<h2>Courses:</h2>
					<ul>
						<li @click="selectSuggestion(course)" v-for="course in filteredCourses" :key="course.name">
							<a>{{ course.name }}</a>
						</li>
					</ul>
					<p class="show-more" @click.prevent="submitSearch('courses')">More courses...</p>
				</div>
				<div id="providers-suggestions" class="suggestion-box">
					<h2>Course providers:</h2>
					<ul>
						<li @click="selectSuggestion(provider)" v-for="provider in filteredProviders"
							:key="provider.name"><a>{{ provider.name }}</a></li>
					</ul>
					<p class="show-more" @click.prevent="submitSearch('providers')">More providers...</p>
				</div>
				<div id="all-suggestions" class="suggestion-box">
					<h2>All results:</h2>
					<ul>
						<li @click="selectSuggestion(suggestion)" v-for="suggestion in getAllSuggestions()"
							:key="suggestion.name"><a>{{ suggestion.name }} [{{ suggestion.type }}]</a></li>
					</ul>
					<p class="show-more" @click.prevent="submitSearch()">More results...</p>
				</div>
			</div>
		</div>
	</v-form>
</template>

<style scoped lang="scss">
.v-form {
	position: relative;
	justify-content: center;
	display: flex;
	align-items: center;
}

#search-suggestions-center-align {
	position: absolute;
	left: 50%;
	top: 100%;
	width: 0;
}

#search-suggestions {
	background-color: rgb(var(--v-theme-secondary));
	border-style: solid;
	border-width: 5px;
	color: rgb(var(--v-theme-text));
	position: relative;
	display: grid;
	height: 40vw;
	max-height: 80vh;
	width: 40vw;
	left: -20vw;
	grid-template-columns: 50% 50%;
	grid-template-rows: 50% 50%;
	overflow: auto;
}

#search-suggestions button {
	color: rgb(var(--v-theme-background));
}

.suggestion-box {
	border-style: solid;
	display: inline-flex;
	flex-direction: column;
	overflow-wrap: break-word;
	word-break: break-word;
	text-align: left;
	padding: 10px;
}

.suggestion-box ul {
	flex-grow: 1;
}

.suggestion-box li {
	margin-left: 15px;
}

.show-more {
	color: rgb(var(--v-theme-primary));
	text-decoration: underline;
}

.suggestion-box a:hover,
.show-more:hover {
	cursor: pointer;
}

#courses-suggestions {
	border-width: 0 2.5px 2.5px 0;
	grid-column-start: 1;
	grid-column-end: 1;
	grid-row-start: 1;
	grid-row-end: 1;
}

#providers-suggestions {
	border-width: 2.5px 2.5px 0 0;
	grid-column-start: 1;
	grid-column-end: 1;
	grid-row-start: 2;
	grid-row-end: 2;
}

#all-suggestions {
	border-width: 0 0 0 2.5px;
	grid-column-start: 2;
	grid-column-end: 2;
	grid-row-start: 1;
	grid-row-end: 3;
}
</style>

<script>
import { ref } from "vue";
import { useRoute, useRouter } from "vue-router";

let suggestions_loaded = false;
export default {
	props: ['lastGlobalClick'],
	methods: {
		searchify(str) {
			return str == null ? str.toLowerCase().replace(/\s/g, "") : str;
		},
		filterCourses(query) {
			this.filteredCourses = this.courses.filter(course => this.searchify(course.name).includes(this.searchify(query)));
		},
		filterProviders(query) {
			this.filteredProviders = this.providers.filter(provider => this.searchify(provider.name).includes(this.searchify(query)));
		},
		getAllSuggestions() {
			return this.filteredCourses.concat(this.filteredProviders);
		},
		selectSuggestion(suggestion) {
			if (suggestion.type == "course" && suggestion.id != null) {
				this.$router.push(`/course/${suggestion.id}`);
				this.showSuggestions = false;
			} else if (suggestion.type == "provider" && suggestion.url) {
				location.href = suggestion.url;
			}
		},
		submitSearch(type, allow_empty_query = true) {
			const params = { q: this.search }
			if (type) {
				params.type = type;
			}
			if (allow_empty_query || this.searchify(params["q"])) {
				this.$router.push({ path: "/search", query: { q: this.search, type: type } })
				this.showSuggestions = false;
			}
		},
		searchBarFocused() {
			this.showSuggestions = true;
			if (!suggestions_loaded) {
				const self = this;
				suggestions_loaded = true;
				this.fetchCourses().then(data => {
					self.courses = data.map(course => { return { type: "course", id: course.courseId, name: course.courseName }; });
					self.filterCourses(self.search);
				});
				this.fetchProviders().then(data => {
					self.providers = data.map(provider => { return { type: "provider", id: provider.courseProviderId, name: provider.providerName, url: provider.url }; });
					self.filterProviders(self.search);
				});
			}
		},
		async fetchCourses() {
			const response = await fetch(this.backendBaseUrl + "courses");
			return await response.json();
		},
		async fetchProviders() {
			const response = await fetch(this.backendBaseUrl + "providers");
			return await response.json();
		}
	},
	watch: {
		search(val) {
			this.filterCourses(val);
			this.filterProviders(val);
		},
		lastGlobalClick(event) {
			if (!this.$refs.searchBar.contains(event.target)) {
				this.showSuggestions = false;
			}
		}
	},
	setup() {
		let search = ref("");
		return { search };
	},
	data() {
		return {
			backendBaseUrl: "http://localhost:8080/",
			showSuggestions: false,
			courses: [],
			providers: [],
			filteredCourses: [],
			filteredProviders: []
		};
	},
	mounted() {
		const vueComponent = this;

		const route = useRoute();
		useRouter().isReady().then(function () {
			if (route.query.q) {
				vueComponent.search = route.query.q;
			}
		})
	}
}
</script>