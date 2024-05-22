<template>

  <div v-if="isLoading">
    <CourseSkeletonLoader></CourseSkeletonLoader>
  </div>
  <div v-else>

	<!--Content wrapper-->
	<div class="course-container" v-if="course != null">
		<h2 class="course-title">{{ course.courseName }}</h2>
		<!-- Info Container for all course details -->
		<section class="info-container">
			<div class="info-item">
				<em class="key">Course Session:</em>
				<span class="value">{{ new Date(course.startDate).toLocaleDateString() }} - {{ new Date(course.endDate).toLocaleDateString() }}</span>
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
			<div v-for="(provider, index) in this.providers" :key="index" class="info-item">
				<em class="key">Provider:</em>
				<span class="value">{{ provider.name }}</span>
				<em class="key">Cost:</em>
				<span class="value">{{ provider.price }}</span>
			</div>
      		<div class="info-item" v-if="course.courseProviderLinks.length === 0">
				<em class="key">Note:</em>
				<span class="value">This course is currently unavailable<br/>Please check again later</span>
			</div>
		</section>
		<div class="info-buttons">
			<nav>
        <v-btn aria-label="Order Course" prepend-icon="mdi-cart-check"
               text="Order Course" @click="orderCourse"
        ></v-btn>
			</nav>
			<v-btn aria-label="Add to Favorites" @click="toggleFavorite" :disabled="waitingForFavoriteToggle"
				:prepend-icon="isFavorite ? 'mdi-heart-off-outline' : 'mdi-heart'">
				{{ isFavorite ? 'Remove Favorite' : 'Add to Favorites' }}
			</v-btn>
		</div>
		<figure v-if="course.image != null && imageUrl != null">
			<img class="course-image" :src=imageUrl :alt="course.image.altText">
		</figure>
		<div id="no-image" class="course-image" v-else>
			<h1>(No image)</h1>
		</div>
		<p class="course-description">
			{{ course.courseDescription }}
		</p>
	</div>
	<div id="no-course" v-if="!course">
		<h1>(404 - Course not found)</h1>
	</div>
  </div>

</template>


<script>
import { watch } from 'vue';
import CourseSkeletonLoader from "@/components/CourseSkeletonLoader.vue";

export default {
	name: 'CoursePage',
  components: {CourseSkeletonLoader},
	data() {
		return {
			course: null,
			imageUrl: null,
			providers: [],
			isFavorite: false,
			waitingForFavoriteToggle: false,
      		isLoading: true,
		};
	},
	methods: {
		toggleFavorite() {
			this.waitingForFavoriteToggle = true;
			this.$authFetchOrPromptLogin(
				this.$backendUrl + "favorites/" + this.course.courseId,
				{ method: this.isFavorite ? "DELETE" : "POST" } // If true, is already favorite, remove it. If false, is not favorite, add it
			).then(response => {
				if (response != null && response.ok) {
					this.isFavorite = !this.isFavorite;
				}
				this.waitingForFavoriteToggle = false;
			});
		},
		formatPriceString(price) {
			return `${(Math.round(price * 100) / 100).toFixed(2)} ${this.$currency.value}`
		},
		updatePrices() {
			for (const provider of this.providers) {
				fetch(`${this.$backendUrl}exchange/${provider.originalCurrency}/${this.$currency.value}/${provider.originalPrice}`)
					.then(response => response.json())
					.then(price => {
						provider.price = this.formatPriceString(price);
						provider.currency = this.$currency;
					});
			}
		},
		async fetchData() {
			const favoriteResponse = await this.$authFetch(this.$backendUrl + "favorites/" + this.$route.params.id);
			if (favoriteResponse != null && favoriteResponse.ok) {
				this.isFavorite = true;
				this.course = (await favoriteResponse.json()).course;
			} else {
				this.isFavorite = false;
				const courseResponse = await fetch(this.$backendUrl + "courses/" + this.$route.params.id);
				if (courseResponse.ok) {
					this.course = await courseResponse.json();
				} else {
					this.course = null;
				}
			}
			this.providers = [];
			if (this.course != null) {
				if (this.course.image != null) {
					const response = await fetch(this.$backendUrl + "images/" + this.course.image.imageId);
					if (response.ok) {
						if (this.imageUrl != null) {
							URL.revokeObjectURL(this.imageUrl);
						}
						this.imageUrl = URL.createObjectURL(await response.blob());
					}
				}
				for (const link of this.course.courseProviderLinks) {
					Promise.all([
						fetch(this.$backendUrl + "providers/" + link.id.courseProviderId)
							.then(response => response.json()),
						fetch(`${this.$backendUrl}exchange/${link.currency}/${this.$currency.value}/${link.price}`)
							.then(response => response.json())
					]).then(values => this.providers.push({
						name: values[0].providerName,
						price: this.formatPriceString(values[1]),
						currency: this.$currency.value,
						originalPrice: link.price,
						originalCurrency: link.currency
					}));
				}
			}
      this.isLoading = false
		},
    orderCourse() {
      this.$router.push({
        name: "Forms",
        params: {courseId: this.course.courseId, title: this.course.courseName},
      });
    },
	},
	created() {
		this.fetchData();
		watch(() => this.$route.params.id, this.fetchData);
		watch(() => this.$currency.value, this.updatePrices);
	},
	unmounted() {
		URL.revokeObjectURL(this.imageUrl);
		this.imageUrl = null;
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
	max-width: 60vw;
	max-height: 40vh;
	height: auto;
	display: block;
	margin: 20px auto;
}

#no-image {
	aspect-ratio: 16 / 9;
	width: 80%;
	max-width: 80%;
	display: flex;
	justify-content: center;
	flex-direction: column;

	h1 {
		color: rgb(var(--v-theme-weakText));
		font-weight: lighter;
	}
}

/* Adjustments specifically for mobile devices */
@media screen and (max-width: 600px) {
	.course-title {
		font-size: 1.5em;
	}

	.info-item {
		flex-direction: row; // Ensure key and value are stacked for clarity
	}

	.course-image {
		max-width: 80%;
	}

	/* For readability on mobile, as it would be too close to the edges otherwise. */
	.course-container {
		margin: 15px 10px 15px 10px;
	}

	.info-buttons .v-btn {
		margin: 2px 4px;
	}
	.info-buttons {
		flex-wrap: wrap;
	}
}

.info-buttons {
	display: flex;
	justify-content: center;
	align-items: center;
	max-width: 80%;
	margin: 0 auto;
	margin-top: 10px;
	gap: 5px;
}

.course-title {
	text-align: center;
	font-size: 1.7em;
	margin-bottom: 10px;
}

.course-description {
	margin: 20px;
	max-width: 60vw;
	text-align: left;
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
