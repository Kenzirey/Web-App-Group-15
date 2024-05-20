<template>
	<div id="filter-container">
		<v-select label="Type" :items="['All results', 'Course', 'Provider']" v-model="type" @update:model-value="filterResults"></v-select>
		<v-select label="Difficulty" :items="['Any', ...this.$difficulties]" v-model="difficulty" @update:model-value="filterResults"
			:disabled="!['all results', 'course'].includes(type.toLowerCase())"></v-select>
		<v-select label="Category" :items="[{categoryName: 'Any'}, ...categories]" v-model="category" item-title="categoryName" @update:model-value="filterResults"
			:disabled="!['all results', 'course'].includes(type.toLowerCase())"></v-select>
	</div>
	<hr>
	<h1>Results:</h1>
	<div id="loading" v-if="!resultsLoaded">
		<h1>(Loading results...)</h1>
	</div>
	<section id="results-container" v-else-if="filteredResults.length > 0">
		<v-pagination :total-visible="6" v-model="page"
			:length="Math.ceil(results.length / items_per_page)"></v-pagination><br>
		<v-data-iterator :items-per-page="items_per_page" :items="filteredResults" :page="page">
			<template v-slot:default="{ items }">
				<template v-for="item in items" :key="item">
					<v-card v-bind="formatResult(item.raw)" @click="selectResult(item.raw)" hover></v-card><br>
				</template>
			</template>
		</v-data-iterator>
		<v-pagination :total-visible="6" v-model="page"
			:length="Math.ceil(results.length / items_per_page)"></v-pagination>
	</section>
	<div id="no-results" v-if="filteredResults.length === 0">
		<h1>(No results)</h1>
	</div>
</template>

<script>
import SearchBar from '@/components/SearchBar.vue';
import { watch } from 'vue';
import { ref } from 'vue';

export default {
	setup() {
		const items_per_page = 20;
		const page = ref(1);
		return { items_per_page, page };
	},
	watch: {
		page() {
			window.scrollTo({ top: 0 });
		}
	},
	methods: {
		capitalizeOnlyFirstLetter(str) {
			return str.substring(0, 1).toUpperCase() + str.substring(1, str.length).toLowerCase();
		},
		selectResult(result) {
			if (result.type === "course") {
				this.$router.push("/course/" + result.id);
			} else if (result.type === "provider") {
				if (result.url) {
					location.href = result.url;
				}
			}
		},
		formatResult(result) {
			if (result.type === "course") {
				return {
					title: result.name,
					subtitle: `Difficulty: ${result.difficulty}`,
					text: result.description,
				}
			} else if (result.type === "provider") {
				return {
					title: result.name,
					subtitle: result.url
				}
			}
		},
		getQueryFilters(query) {
			if (query.type) {
				this.type = this.capitalizeOnlyFirstLetter(query.type);
			}
			if (query.category) {
				fetch(this.$backendUrl + "categories/" + query.category)
				.then(response => {
					if (response.ok) {
						response.json().then(category => {
							category.categoryName = this.capitalizeOnlyFirstLetter(category.categoryName);
							this.category = category;
						})
					}
				})
			}
			if (query.difficulty) {
				this.difficulty = this.capitalizeOnlyFirstLetter(query.difficulty);
			}
		},
		filterResults() {
			// I initially wanted any changes to search filters to update the query parameters in the address bar, however
			// Vue router refuses to let me change them through "router.$push" & "$router.replace", for unknown reasons
			this.resultsLoaded = false;
			this.filteredResults = this.results;
			if (this.type.toLowerCase() !== "all results") {
				this.filteredResults = this.filteredResults.filter(result => result.type.toLowerCase() === this.type.toLowerCase())
			}
			if (this.category.categoryName.toLowerCase() !== "any") {
				this.filteredResults = this.filteredResults.filter(result =>
					result.type.toLowerCase() !== "course"
					|| result.categories.map(c => c.categoryId).includes(this.category.id)
				);
			}
			if (this.difficulty.toLowerCase() !== "any") {
				this.filteredResults = this.filteredResults.filter(result =>
					result.type.toLowerCase() !== "course" || result.difficulty.toLowerCase() === this.difficulty.toLowerCase()
				);
			}
			this.resultsLoaded = true;
		},
		loadResults() {
			Promise.all([
				fetch(this.$backendUrl + "courses")
					.then(response => response.json())
					.then(data => SearchBar.methods.standardizeCourses(data))
					.then(courses => SearchBar.methods.filterResults(courses, this.$route.query.q)),
				fetch(this.$backendUrl + "providers")
					.then(response => response.json())
					.then(data => SearchBar.methods.standardizeProviders(data))
					.then(providers => SearchBar.methods.filterResults(providers, this.$route.query.q))
			]).then(values => {
				this.results = [...values[0], ...values[1]];
				this.filterResults();
			})
		}
	},
	data() {
		return {
			categories: [],
			resultsLoaded: false,
			results: [],
			filteredResults: [],
			query: "",
			type: "All results",
			category: "Any",
			difficulty: "Any"
		};
	},
	created() {
		watch(() => this.$route.query, query => {
			this.getQueryFilters(query);
			this.filterResults();
		});
		this.getQueryFilters(this.$route.query);
		fetch(this.$backendUrl + "categories").then(response => response.json()).then(categories => this.categories = categories);
		this.loadResults();
	}
}
</script>

<style scoped lang="scss">
hr {
	color: rgb(var(--v-theme-separator));
	margin-top: 10px;
	margin-bottom: 10px;
}

#filter-container,
#results-container {
	width: 80vw;
}

#filter-container {
	display: flex;
	gap: 10px;
	width: 100%;

	* {
		flex-grow: 0;
	}
}

#results-container, h1 {
	text-align: left;
}

#loading, #no-results {
	width: 80vw;
	height: 50vh;
	display: flex;
	justify-content: center;
	flex-direction: column;

	h1 {
		text-align: center;
		color: rgb(var(--v-theme-weakText));
		font-weight: lighter;
	}
}
</style>
