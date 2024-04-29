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
					title: result.courseName,
					subtitle: `Difficulty: ${result.difficultyLevel}`,
					text: result.courseDescription,
				}
			} else if (result.type == "provider") {
				return {
					title: result.providerName,
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
		watchQuery(val) {
			console.log(val);
		}
	},
	data() {
		return {
			results: [],
			query: ""
		};
	},
	mounted() {
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

		let all;
		combinedPromise.then(() => all = [...courses, ...providers]).then(() => {
			this.query = this.$route.query.q;
			if (this.$route.query.q) {
				watch(this.$route.query.q, this.watchQuery);
				this.sortExactMatches(all, this.$route.query.q)
			}
		}).then(() => this.results = all);


	}
}
</script>