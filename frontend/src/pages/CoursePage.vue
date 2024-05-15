<template>
	<!--Content wrapper-->
	<div class="course-container" v-if="course != null">
		<h2 class="course-title">{{ course.courseName }}</h2>
		<!-- Info Container for all course details -->
		<section class="info-container">
			<div class="info-item">
				<em class="key">Course Session:</em>
				<span class="value">{{ course.startDate }} - {{ course.endDate }} (TODO: Format this)</span>
			</div>
			<div class="info-item">
				<em class="key">Difficulty Level:</em>
				<span class="value">{{ course.difficultyLevel }}</span>
			</div>
			<div class="info-item">
				<em class="key">Course Size:</em>
				<span class="value">{{ course.courseCredits }}</span>
			</div>
			<div class="info-item">
				<em class="key">Hours per week:</em>
				<span class="value">{{ course.hoursPerWeek }}</span>
			</div>
			<div class="info-item">
				<em class="key">Related Certifications:</em>
				<span class="value">{{ course.relatedCertification }}</span>
			</div>
			<!-- Provider and Cost -->
			<div v-for="(provider, index) in course.courseProvider" :key="index" class="info-item">
				<em class="key">Provider:</em>
				<span class="value">{{ provider.providerName }}</span>
				<em class="key">Cost:</em>
				<span class="value">$N/A (We don't have this in our database yet)</span>
			</div>
		</section>
		<div class="info-buttons">
			<nav>
				<v-btn
          aria-label="Order Course"
          prepend-icon="mdi-cart-check"
          text="Order Course"
          @click="orderCourse"
        >
			</nav>
			<v-btn aria-label="Add to Favorites" @click="toggleFavorite" :disabled="waitingForFavoriteToggle || !jwt"
				:prepend-icon="isFavorite ? 'mdi-heart-off-outline' : 'mdi-heart'">
				{{ isFavorite ? 'Remove Favorite' : 'Add to Favorites' }}
			</v-btn>
		</div>
		<figure>
			<img class="course-image" v-if="course.images.length > 0" :src=course.images[0]
				alt="TODO: We don't have this in our database yet">
			<figcaption v-if="course.images.length > 0">TODO: We don't have this in our database yet</figcaption>
			<div id="no-image" class="course-image" v-if="course.images.length == 0">
				<h1>(No image)</h1>
			</div>
		</figure>
		<p class="course-description">
			{{ course.courseDescription }}
		</p>
	</div>
	<div id="no-course" v-if="!course">
		<h1>(404 - Course not found)</h1>
	</div>
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
		},
    orderCourse() {
      this.$router.push({
        name: "Forms",
        params: { courseId: this.course.id, title: this.course.title },
      });
    },
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
	}

	/* For readability on mobile, as it would be too close to the edges otherwise. */
	.course-container {
		margin: 15px 10px 15px 10px;
	}

	.info-buttons .v-btn {
		margin: 2px 4px;
	}
}

.info-buttons {
	display: flex;
	justify-content: center;
	align-items: center;
	max-width: 80%;
	margin: 0 auto;
	margin-top: 10px;
	flex-wrap: wrap;
}

.course-title {
	text-align: center;
	font-size: 1.7em;
	margin-bottom: 10px;
}

.info-container {
	display: flex;
	flex-direction: column;
	align-items: flex-start; //Left align text for readability. (Dyslexia)
	justify-content: center;
	max-width: 450px;
	margin: 0 auto;
}

.info-item {
	display: flex;
	align-items: center;
	justify-content: flex-start;
	margin-bottom: 5px;
	text-align: left;
}


.key,
.value {
	margin-right: 8px;
}

.key {
	font-weight: bold;
	white-space: nowrap;
}


.v-btn {
	background-image: linear-gradient(to right, rgb(var(--v-theme-gradiantOne)), rgb(var(--v-theme-gradiantTwo)));
	color: rgb(var(--v-theme-background));
}
</style>
