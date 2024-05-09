<template>
	<v-pagination :total-visible="6" v-model="page" :length="Math.ceil(results.length / items_per_page)"></v-pagination>
	<v-data-iterator :items-per-page="items_per_page" :items="results" :page="page">
		<template v-slot:default="{ items }">
			<template v-for="(item, i) in items" :key="i">
				<v-card v-bind="formatResult(item.raw)" @click="selectResult(item.raw)" hover></v-card>

				<br>
			</template>
		</template>
	</v-data-iterator>
	<v-pagination :total-visible="6" v-model="page" :length="Math.ceil(results.length / items_per_page)"></v-pagination>
</template>

<script>
import SearchBar from '@/components/SearchBar.vue';
import { watch } from 'vue';
import { ref } from 'vue';

export default {
	setup() {
		const page = ref(1);
		const items_per_page = 20;
		return { page, items_per_page };
	},
	watch: {
		page() {
			window.scrollTo({ top: 0 });
		}
	},
	methods: {
		selectResult(result) {
			if (result.type == "course") {
				const query = new URLSearchParams();
				query.append("id", result.courseId);
				if (query.get("id")) {
					location.href = "./course?" + query.toString();
				}
			} else if (result.type == "provider") {
				if (result.url) {
					location.href = result.url;
				}
			}
		},
		formatResult(result) {
			if (result.type == "course") {
				return {
					title: result.name,
					subtitle: `Difficulty: ${result.difficulty}`,
					text: result.description,
				}
			} else if (result.type == "provider") {
				return {
					title: result.name,
					subtitle: result.url
				}
			}
		},
		getName(obj) {
			return obj.type == "course" ? obj.courseName : (obj.type == "provider" ? obj.providerName : "");
		},
		sortExactMatches(array, query) {
			array.sort((a, b) => (this.getName(b) == query) - (this.getName(a) == query));
		},
		loadResults() {
			const backend_base_url = "http://localhost:8080/";

			const coursesPromise = fetch(backend_base_url + "courses")
				.then(response => response.json())
				.then(data => SearchBar.methods.standardizeCourses(data))
				.then(courses => SearchBar.methods.filterResults(courses, this.$route.query.q, this.$route.query.difficulty ? this.$route.query.difficulty.toLowerCase() : null));
			
			const providersPromise = fetch(backend_base_url + "providers")
				.then(response => response.json())
				.then(data => SearchBar.methods.standardizeProviders(data))
				.then(providers => SearchBar.methods.filterResults(providers, this.$route.query.q, this.$route.query.difficulty ? this.$route.query.difficulty.toLowerCase() : null));

			Promise.all([coursesPromise, providersPromise]).then(responses => {
				const courses = responses[0];
				const providers = responses[1];

				const type = this.$route.query.type ? this.$route.query.type.toLowerCase() : null;
				const category = this.$route.query.category;
				console.log(courses);
				console.log(providers);
				this.results = [...courses, ...providers];
				console.log(this.results);
				if (type) {
					this.results = this.results.filter(result => result.type.toLowerCase() == type);
				}
				/* TODO: Make courses have categories, so that this filter is appliccable
				if (category) {
					all = all.filter(result => result.type.toLowerCase() != "course" || result.category == category);
				}
				*/
			})
		},
		loadResultsOld() {
			const backend_base_url = "http://localhost:8080/";
			let coursesPromise;
			let providersPromise;

			const courses = [];
			const providers = [];
			if (this.$route.query.type != "providers") {
				coursesPromise = fetch(backend_base_url + (this.$route.query.q ? `courses/search/${this.$route.query.q}` : "courses"))
					.then(response => response.json())
					.then(data => data.map(course => {
						course.type = "course";
						return course;
					}))
					.then(courseArray => courses.push(...courseArray));
			}
			if (this.$route.query.type != "courses") {
				providersPromise = fetch(backend_base_url + (this.$route.query.q ? `providers/search/${this.$route.query.q}` : "providers"))
					.then(response => response.json())
					.then(data => data.map(provider => {
						provider.type = "provider";
						return provider;
					}))
					.then(providerArray => providers.push(...providerArray));
			}
			
			let combinedPromise;
			if (providersPromise == null) {
				combinedPromise = coursesPromise;
			} else if (coursesPromise == null) {
				combinedPromise = providersPromise;
			} else {
				combinedPromise = Promise.all([coursesPromise, providersPromise]);
			}

			combinedPromise.then(() => [...courses, ...providers]).then(all => {
				if (this.$route.query.q) {
					this.sortExactMatches(all, this.$route.query.q)
				}
				this.results = all;
			});
		}
	},
	data() {
		return {
			results: [],
			query: ""
		};
	},
	mounted() {
		watch(() => this.$route.query, this.loadResults);
		this.loadResults();
	}
}
</script>