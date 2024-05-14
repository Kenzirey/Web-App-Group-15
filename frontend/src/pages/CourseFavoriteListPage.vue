<template>
	<div class="flex-container">
		<h1>Favorite Courses</h1>
		<p>I pulled Tony's fire alarm again, this time on purpose</p>
		<section class="favorite-items" v-if="favoriteCourses.length != 0">
			<div class="favorite-course-card" v-for="course in favoriteCourses" :key="course.courseId">
				<v-card class="favorite-card-container">
					<v-card-title class="card-title-container">
						<span class="course-title">{{ course.courseName }}</span>
						<v-btn class="remove-favorite-button" icon="mdi-heart-off-outline" variant="plain"
							density="comfortable" text @click="removeFromFavorites(course.courseId)">
						</v-btn>
					</v-card-title>
					<v-card-text class="course-description">
						{{ course.courseDescription }}
					</v-card-text>
				</v-card>
			</div>
		</section>
		<section id="no-results" v-if="favoriteCourses.length == 0">
			<h1>(No favorites)</h1>
		</section>
	</div>
</template>


<script>
import { getCookie } from '@/utility/cookieHelper';
import { store } from '@/utility/store';

//ChatGPT helped with the json.stringify and filters.
export default {
	name: 'CourseFavoriteListPage',
	data() {
		return {
			favoriteCourses: []
		};
	},
	setup() {
		return { jwt: getCookie("authToken") }
	},
	created() {
		this.loadFavorites();
	},
	methods: {
		loadFavorites() {
			if (store.user.isLoggedIn && this.jwt) {
				fetch(this.$backendUrl + "favorites", { headers: { Authorization: "Bearer " + this.jwt } })
					.then(response => response.json())
					.then(data => this.favoriteCourses = data.map(favorite => favorite.course));
			}
		},
		removeFromFavorites(courseId) {
			if (store.user.isLoggedIn && this.jwt) {
				fetch(this.$backendUrl + "favorites/" + courseId, { method: "DELETE", headers: { Authorization: "Bearer " + this.jwt } })
					.then(response => {
						if (response.ok) {
							const removedCourse = this.favoriteCourses.find(course => course.courseId == courseId);
							if (removedCourse) {
								this.favoriteCourses.splice(this.favoriteCourses.indexOf(removedCourse), 1);
							}
						}
					});
			}
		}
	}
};

</script>

<style scoped lang="scss">

#no-results {
	height: 60vh;
	display: flex;
	justify-content: center;
	flex-direction: column;

	h1 {
		color: rgb(var(--v-theme-weakText));
		font-weight: lighter;
	}
}

.card-title-container {
	display: grid;
	grid-template-columns: 1fr auto;
	align-items: center;
	position: relative;
	/* Needed to position the button absolutely within the container */
}

.course-title {
	grid-column: 1 / -1;
	text-align: center;
	z-index: 1;
	/* In case of button overlapping, changed z-index */
}

/* Chatgpt helped with this CSS */
.remove-favorite-button {
	position: absolute;
	right: 0;
	top: 50%;
	/* Center vertically */
	transform: translateY(-50%);
	z-index: 2;
}

.favorite-items {
	margin-top: 20px;
}

.favorite-course-card {
	margin-bottom: 20px;
}
</style>
