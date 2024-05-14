<template>
	<!--Content wrapper-->
	<main class="course-container" v-if="course != null">
		<h2 class="course-title">{{ course.courseName }}</h2>
		<!-- Info Container for all course details -->
		<section class="info-container">
			<div class="session-date">
				<span class="key">Course Session:</span>
				<span class="value">{{ course.startDate }} - {{ course.endDate }} (TODO: Format this)</span>
			</div>
			<div class="info-item">
				<span class="key">Difficulty Level:</span>
				<span class="value">{{ course.difficultyLevel }}</span>
			</div>
			<div class="info-item">
				<span class="key">Course Size:</span>
				<span class="value">{{ course.courseCredits }}</span>
			</div>
			<div class="info-item">
				<span class="key">Hours per week:</span>
				<span class="value">{{ course.hoursPerWeek }}</span>
			</div>
			<div class="info-item">
				<span class="key">Related Certifications:</span>
				<span class="value">{{ course.relatedCertification }}</span>
			</div>
			<!-- Provider and Cost -->
			<div v-for="(provider, index) in course.courseProvider" :key="index" class="info-item">
				<span class="key">Provider:</span>
				<span class="value">{{ provider.providerName }}</span>
				<span class="key">Cost:</span>
				<span class="value">$N/A (We don't have this in our database yet)</span>
			</div>
		</section>
		<div class="info-buttons">
			<nav>
				<v-btn aria-label="Order Course" prepend-icon="mdi-cart-check" text="Order Course" type="apply"
					href="/forms" variant="outlined"></v-btn>
			</nav>
			<v-btn aria-label="Add to Favorites" @click="toggleFavorite" :disabled="waitingForFavoriteToggle || !jwt"
				:prepend-icon="isFavorite ? 'mdi-heart-off-outline' : 'mdi-heart'">
				{{ isFavorite ? 'Remove Favorite' : 'Add to Favorites' }}
			</v-btn>
		</div>
		<figure>
			<img class="course-image" v-if="course.images.length > 0" :src=course.images[0]
				alt="TODO: We don't have this in out database yet">
			<div id="no-image" class="course-image" v-if="course.images.length == 0">
				<h1>(No image)</h1>
			</div>
		</figure>
		<p class="course-description">
			{{ course.courseDescription }}
		</p>
	</main>
	<div id="no-course" v-if="!course"><h1>(404 - Course not found)</h1></div>
</template>


<script>
import { getCookie } from '@/utility/cookieHelper';
import { store } from '@/utility/store';
import { watch } from 'vue';

//TODO: remove the console debugging lines before deploying our project.
export default {
	name: 'CoursePage',
	data() {
		return {
			course: null,
			isFavorite: false,
			waitingForFavoriteToggle: false,
		};
	},
	methods: {
		toggleFavorite() {
			this.waitingForFavoriteToggle = true;
			fetch(this.$backendUrl + "favorites/" + this.course.courseId,
				{
					method: this.isFavorite ? "DELETE" : "POST", // If true, is already favorite, remove it. If false, is not favorite, add it
					headers: { Authorization: "Bearer " + this.jwt }
				}
			).then(response => {
				if (response.ok) {
					this.isFavorite = !this.isFavorite;
				}
				this.waitingForFavoriteToggle = false;
			});
		},
		async fetchData() {
			this.isFavorite = false;
			this.course = null;
			if (this.jwt) {
				const response = await fetch(this.$backendUrl + "favorites/" + this.$route.params.id, { headers: { Authorization: "Bearer " + this.jwt } });
				if (response.ok) {
					this.isFavorite = true;
					this.course = (await response.json()).course;
				}
			}
			if (this.course == null) {
				const response = await fetch(this.$backendUrl + "courses/" + this.$route.params.id);
				if (response.ok) {
					this.course = await response.json();
				}
			}
		}
	},
	setup() {
		return { jwt: store.user.isLoggedIn ? getCookie("authToken") : null };
	},
	created() {
		this.fetchData();
		watch(() => this.$route.params.id, this.fetchData);
	}
}

</script>

<style lang="scss" scoped>
#no-course {
	height: 60vh;
	display: flex;
	justify-content: center;
	flex-direction: column;

	h1 {
		color: rgb(var(--v-theme-weakText));
		font-weight: lighter;
	}
}

.course-image {
	/* To reduce the size of the image */
	max-width: 60%;
	height: auto;
	display: block;
	margin: 8px auto;
}

#no-image {
	aspect-ratio: 16 / 9;
	width: 60%;
	display: flex;
	justify-content: center;
	flex-direction: column;

	h1 {
		color: rgb(var(--v-theme-weakText));
		font-weight: lighter;
	}
}

/* Adjustments specifically for mobile devices */
@media screen and (max-width: 479px) {
	.course-title {
		font-size: 1.5em;
	}

	.info-item {
		flex-direction: row; // Ensure key and value are stacked for clarity
	}

	.course-image {
		max-width: 80%;
	}

	#no-image {
		width: 80%;
		height: calc(80% / 16 * 9);
	}
}

.info-buttons {
	display: flex;
	justify-content: center;
	align-items: center;
	max-width: 80%;
	margin: 0 auto;
	flex-wrap: wrap;
}

.info-buttons {
	display: flex;
	align-items: center;
	max-width: 80%;
	flex-wrap: wrap;
}

.course-title {
	text-align: center;
	font-size: 1.7em;
}

.info-container {
	display: flex;
	flex-direction: column;
}

.info-item {
	display: flex;
	align-items: center;
	justify-content: center;
	margin: 5px 5px 5px 5px;
}

.key,
.value {
	margin-right: 8px;
}

.key {
	font-weight: bold;
}

.v-btn {
	background-image: linear-gradient(to right, rgb(var(--v-theme-gradiantOne)), rgb(var(--v-theme-gradiantTwo)));
	color: rgb(var(--v-theme-background));
}
</style>