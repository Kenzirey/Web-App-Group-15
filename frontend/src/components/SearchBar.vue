<template>
	<v-form ref="searchBar" @submit.prevent="submitSearch">
		<!-- Prevents other buttons from being invoked as the submit when clicking enter -->
		<v-text-field @keydown.tab="showSuggestions = false" id="search-bar" @click="searchBarFocused"
			@focus="searchBarFocused" v-model="search" append-icon="mdi-magnify"
			@click:append="submitSearch" placeholder="Search..." required autocomplete="off"
			hide-details />
		<div id="search-suggestions-center-align">
			<div v-if="showSuggestions" id="search-suggestions">
				<v-chip-group id="difficulties" class="suggestion-box">
					<v-chip>Featured</v-chip>
					<v-chip>Beginner</v-chip>
					<v-chip>Advanced</v-chip>
					<v-chip>Expert</v-chip>
				</v-chip-group>
				<div id="courses-suggestions" class="suggestion-box">
					<h2>Courses:</h2>
					<ul>
						<li v-for="course in filteredCourses" :key="course.name">
							<router-link @click="showSuggestions = false" :to="'/course/' + course.id">{{ course.name }}</router-link>
						</li>
					</ul>
					<router-link @click="showSuggestions = false" :to="{path: '/search', query: {type: 'courses', q: this.search}}">More courses...</router-link>
				</div>
				<div id="categories-suggestions" class="suggestion-box">
					<h2>Categories:</h2>
					<ul>
						<li v-for="category in filteredCategories" :key="category.name">
							<router-link @click="showSuggestions = false" :to="{path: '/search', query: {category: category.id}}">{{ category.name }}</router-link>
						</li>
					</ul>
				</div>
				<div id="all-suggestions" class="suggestion-box">
					<h2>All Results:</h2>
					<ul>
						<li v-for="suggestion in allSuggestionsFiltered" :key="suggestion.name">
							<a v-if="suggestion.type == 'provider'" :href="suggestion.url">
								{{ suggestion.name }} [{{ suggestion.type }}]
							</a>
							<router-link @click="showSuggestions = false"
								v-if="suggestion.type != 'provider'"
								:to="suggestion.type == 'course' ? '/course/' + suggestion.id : suggestion.type == 'category' ? {path: '/search', query: {category: suggestion.id}} : null"
							>
								{{ suggestion.name }} [{{ suggestion.type }}]
							</router-link>
						</li>
					</ul>
					<router-link @click="showSuggestions = false" :to="{path: '/search', query: {q: this.search}}">More results...</router-link>
				</div>
			</div>
		</div>
	</v-form>
</template>

<script>
import { ref } from "vue";
import { useRoute, useRouter } from "vue-router";

let suggestions_loaded = false;
export default {
	props: ['lastGlobalClick'],
	computed: {
		allSuggestionsFiltered() {
			return this.filteredCourses.concat(this.filteredProviders).concat(this.filteredCategories);
		}	
	},
	methods: {
		searchify(str) {
			return str != null ? str.toLowerCase().replace(/\s/g, "") : "";
		},
		filterResults(results, query) {
			return results.filter(result => this.searchify(result.name).includes(this.searchify(query)));
		},
		submitSearch() {
			const query = this.searchify(this.search);
			if (query) {
				this.$router.push({ path: "/search", query: { q: query } })
				this.showSuggestions = false;
			}
		},
		searchBarFocused() {
			this.showSuggestions = true;
			if (!suggestions_loaded) {
				suggestions_loaded = true;
				this.fetchCourses();
				this.fetchProviders();
				this.fetchCategories();
			}
		},
		standardizeCourses(courses) {
			return courses.map(course => { return {
				type: "course",
				id: course.courseId,
				name: course.courseName,
				description: course.courseDescription,
				difficulty: course.difficultyLevel
			}})
		},
		standardizeProviders(providers) {
			return providers.map(provider => { return {
				type: "provider",
				id: provider.courseProviderId,
				name: provider.providerName,
				url: provider.url
			}; });
		},
		standardizeCategories(categories) {
			return categories.map(category => { return {
				type: "category",
				id: category.categoryId,
				name: category.categoryName
			}; });
		},
		async fetchCourses() {
			const response = await fetch(this.backendBaseUrl + "courses");
			this.courses = this.standardizeCourses(await response.json());
			this.filteredCourses = this.filterResults(this.courses, this.search);
		},
		async fetchProviders() {
			const response = await fetch(this.backendBaseUrl + "providers")
			this.providers = this.standardizeProviders(await response.json());
			this.filteredProviders = this.filterResults(this.providers, this.search);
		},
		async fetchCategories() {
			const response = await fetch(this.backendBaseUrl + "categories");
			this.categories = this.standardizeCategories(await response.json())
			this.filteredCategories = this.filterResults(this.categories, this.search);
		}
	},
	watch: {
		search(query) {
			this.filteredCourses = this.filterResults(this.courses, query);
			this.filteredProviders = this.filterResults(this.providers, query);
			this.filteredCategories = this.filterResults(this.categories, query);
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
			categories: [],
			filteredCourses: [],
			filteredProviders: [],
			filteredCategories: []
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
	background-color: rgb(var(--v-theme-surface));
	border-style: solid;
	border-width: 5px;
	color: rgb(var(--v-theme-text));
	position: relative;
	display: grid;
	height: 40vw;
	max-height: 80vh;
	width: 40vw;
	left: -20vw;
	grid-template-columns: minmax(0, 1fr) minmax(0, 1fr);
	grid-template-rows: auto minmax(0, 1fr) minmax(0, 1fr);
	overflow: auto;
}

#search-suggestions a {
	text-decoration: none;
}

#search-suggestions a:hover {
	text-decoration: underline;
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

#difficulties {
	align-items: center;
	border-width: 0 0 2.5px 0;
	grid-column-start: 1;
	grid-column-end: 3;
	grid-row-start: 1;
	grid-row-end: 2;
}

#courses-suggestions {
	border-width: 2.5px 2.5px 2.5px 0;
	grid-column-start: 1;
	grid-column-end: 2;
	grid-row-start: 2;
	grid-row-end: 3;
}

#categories-suggestions {
	border-width: 2.5px 2.5px 0 0;
	grid-column-start: 1;
	grid-column-end: 2;
	grid-row-start: 3;
	grid-row-end: 4;
}

#all-suggestions {
	border-width: 2.5px 0 0 2.5px;
	grid-column-start: 2;
	grid-column-end: 3;
	grid-row-start: 2;
	grid-row-end: 4;
}
</style>