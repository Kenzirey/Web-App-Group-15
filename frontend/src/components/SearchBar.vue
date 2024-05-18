<template>
	<v-form ref="searchBar" @submit.prevent="() => submitSearch(getQuery())">
		<!-- Prevents other buttons from being invoked as the submit when clicking enter -->
		<v-text-field @keydown.tab="showSuggestions = false" id="search-bar" @click="searchBarFocused"
			@focus="searchBarFocused" v-model="search" append-inner-icon="mdi-magnify"
			@click:append-inner="() => { appendIconJustClicked = true; submitSearch(getQuery()) }"
			placeholder="Search..." required autocomplete="off" hide-details></v-text-field>
		<div v-if="showSuggestions" id="search-suggestions">
			<v-chip-group v-model="difficultyIndex" id="difficulties" class="suggestion-box">
				<v-chip v-for="difficulty in difficulties" :key="difficulty">{{ difficulty }}</v-chip>
			</v-chip-group>
			<div id="courses-suggestions" class="suggestion-box">
				<h2>Courses:</h2>
				<ul>
					<li v-for="course in filteredCourses" :key="course.name">
						<router-link @click="showSuggestions = false" :to="'/course/' + course.id">{{ course.name
							}}</router-link>
					</li>
				</ul>
				<router-link @click="showSuggestions = false" :to="{ path: '/search', query: getQuery('course') }">More
					courses...</router-link>
			</div>
			<div id="categories-suggestions" class="suggestion-box">
				<h2>Categories:</h2>
				<ul>
					<li v-for="category in filteredCategories" :key="category.name">
						<router-link @click="showSuggestions = false"
							:to="{ path: '/search', query: getQuery('course', category.id, false) }">{{ category.name
							}}</router-link>
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
						<router-link @click="showSuggestions = false" v-if="suggestion.type != 'provider'"
							:to="suggestion.type == 'course' ? '/course/' + suggestion.id : suggestion.type == 'category' ? { path: '/search', query: getQuery('course', suggestion.id, false) } : null">
							{{ suggestion.name }} [{{ suggestion.type }}]
						</router-link>
					</li>
				</ul>
				<router-link @click="showSuggestions = false" :to="{ path: '/search', query: getQuery() }">More
					results...</router-link>
			</div>
		</div>
		<div id="search-suggestions-center-align">
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
		filterResults(results, query, difficulty) {
			return results.filter(result =>
				this.searchify(result.name).includes(this.searchify(query))
				&& (!difficulty || result.type.toLowerCase() != "course" || result.difficulty.toLowerCase() == difficulty.toLowerCase()));
		},
		getQuery(type, category, includeSearchQuery = true) {
			const query = {};
			if (type) {
				query.type = type.toLowerCase();
			}
			if (category && (!query.type || query.type == "course")) {
				query.category = category;
			}
			if (this.selectedDifficulty && (!query.type || query.type == "course")) {
				query.difficulty = this.selectedDifficulty.toLowerCase();
			}
			const searchQuery = this.searchify(this.search);
			if (includeSearchQuery && searchQuery) {
				query.q = searchQuery;
			}
			return query;
		},
		submitSearch(query) {
			if (query.q) {
				this.$router.push({ path: "/search", query: query })
				this.showSuggestions = false;
			}
		},
		searchBarFocused() {
			if (this.appendIconJustClicked) {
				this.appendIconJustClicked = false;
			} else {
				this.showSuggestions = true;
				if (!suggestions_loaded) {
					suggestions_loaded = true;
					this.fetchCourses();
					this.fetchProviders();
					this.fetchCategories();
				}
			}
		},
		standardizeCourses(courses) {
			return courses.map(course => {
				return {
					type: "course",
					id: course.courseId,
					name: course.courseName,
					description: course.courseDescription,
					difficulty: course.difficultyLevel
				}
			})
		},
		standardizeProviders(providers) {
			return providers.map(provider => {
				return {
					type: "provider",
					id: provider.courseProviderId,
					name: provider.providerName,
					url: provider.url
				};
			});
		},
		standardizeCategories(categories) {
			return categories.map(category => {
				return {
					type: "category",
					id: category.categoryId,
					name: category.categoryName
				};
			});
		},
		async fetchCourses() {
			const response = await fetch(this.$backendUrl + "courses");
			this.courses = this.standardizeCourses(await response.json());
			this.filteredCourses = this.filterResults(this.courses, this.search);
		},
		async fetchProviders() {
			const response = await fetch(this.$backendUrl + "providers")
			this.providers = this.standardizeProviders(await response.json());
			this.filteredProviders = this.filterResults(this.providers, this.search);
		},
		async fetchCategories() {
			const response = await fetch(this.$backendUrl + "categories");
			this.categories = this.standardizeCategories(await response.json())
			this.filteredCategories = this.filterResults(this.categories, this.search);
		},
		updateFilteredResults(query, difficulty) {
			this.filteredCourses = this.filterResults(this.courses, query, difficulty);
			this.filteredProviders = this.filterResults(this.providers, query, difficulty);
			this.filteredCategories = this.filterResults(this.categories, query, difficulty);
		}
	},
	watch: {
		search(query) {
			this.updateFilteredResults(query, this.selectedDifficulty);
		},
		difficultyIndex(index) {
			this.selectedDifficulty = this.difficulties[index];
		},
		selectedDifficulty(difficulty) {
			this.updateFilteredResults(this.search, difficulty);
		},
		lastGlobalClick(event) {
			if (!this.$refs.searchBar.contains(event.target)) {
				this.showSuggestions = false;
			}
		}
	},
	setup() {
		let search = ref("");
		let difficultyIndex = ref(null);
		return { search, difficultyIndex };
	},
	data() {
		return {
			appendIconJustClicked: false,
			showSuggestions: false,
			difficulties: ["Beginner", "Advanced", "Expert"],
			selectedDifficulty: null,
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
	position: absolute;
	left: 0;
	top: 60px;
	display: grid;
	width: calc(100% + 200px);
	height: calc(85vh - 50px);
	grid-template-columns: minmax(0, 1fr) minmax(0, 1fr);
	grid-template-rows: auto minmax(0, 1fr) minmax(0, 1fr);
	overflow: auto;
}

.test {
	background-color: red;
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

@media screen and (max-width: 1024px) {
	#search-suggestions {
		left: -50px;
		width: calc(100% + 300px);
	}
}

@media screen and (max-width: 768px) {
	#search-suggestions {
		width: calc(100% + 100px);
	}

	#courses-suggestions {
		display: none;
	}

	#categories-suggestions {
		display: none;
	}

	#all-suggestions {
		border-width: 2.5px 0 0 0;
		grid-column-start: 1;
		grid-column-end: 3;
		grid-row-start: 2;
		grid-row-end: 4;
	}
}

@media screen and (max-width: 480px) {
	#search-suggestions {
		left: 0;
	}
}
</style>