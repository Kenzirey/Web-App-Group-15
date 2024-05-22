<template>
	<div class="flex-container">
		<h1>Favorite Courses</h1>
		<section class="favorite-items" v-if="favoriteCourses.length != 0">
			<div class="favorite-course-card" v-for="course in favoriteCourses" :key="course.courseId">
				<v-card class="favorite-card-container">
					<v-card-title class="card-title-container">
						<span class="course-title">{{ course.courseName }}</span>
						<v-btn class="remove-favorite-button" icon="mdi-heart-off-outline" variant="plain"
							density="comfortable" text @click="removeFromFavorites(course.courseId)">
						</v-btn>
					</v-card-title>
					<v-card-text class="course-category">
						{{ course.difficultyLevel }}
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
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.course-title {
	flex-grow: 1;
	margin-right: 10px;
	text-align: left;
	/* Made it left for consistency, in terms of the left-aligned dyslexic thing */
	white-space: normal;
}

.favorite-items {
	margin-top: 20px;
}

.favorite-course-card {
	margin-bottom: 20px;
	width: 100%;
	max-width: 500px;
}

@media screen and (max-width: 767px) {
	.card-title-container {
		flex-direction: column;
		align-items: center;
	}

	.course-title {
		text-align: center;
	}

	.remove-favorite-button {
		order: -1;
		width: 100%;
		margin-bottom: 5px;
	}

	.favorite-course-card {
		max-width: 300px;
		height: auto;
	}
}
</style>