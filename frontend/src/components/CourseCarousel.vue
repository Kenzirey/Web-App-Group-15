<template>
	<v-card>
		<v-card-title>
			<router-link
				:to="{path: 'search', query: {type: 'course', difficulty: difficulty.toLowerCase() == 'sale' ? 'featured' : difficulty.toLowerCase()}}">
				{{ difficulty.toLowerCase() == "sale" ? "Featured" : difficulty }} courses
			</router-link>
		</v-card-title>

		<swiper-container @swipernavigationprev="resetSlideTimer" @swipernavigationnext="resetSlideTimer" ref="carousel"
						  :slides-per-view="slidesPerView" :loop="loopMode" css-mode="true" navigation="true"
						  pagination="true">
			<swiper-slide v-for="course in filteredCourses">
				<div class="bullet-positioning-box">
					<router-link :to="'/course/' + course.courseId">
						<v-img v-if="course.image" alt="course.alt" :src="course.image.url" :aspect-ratio="aspectRatio"
							   cover></v-img>
						<div v-else class="no-image"><h3>(No image)</h3></div>
						{{ course.courseName }}
					</router-link>
				</div>
			</swiper-slide>
		</swiper-container>
	</v-card>

</template>

<script>
export default {

	/**
	 * In vue, props are custom attributes one can register to a component.
	 * When a value is passed to a prop attribute, it comes a property on that component instance.
	 * We do this as we want to be able to filter what courses are added into the carousel, per "category"
	 * or by "sale".
	 * These become basically "props" for our filter criteria.
	 */
	props: {
		courses: Array,
		difficulty: String,
	},
	computed: {
		filteredCourses() {
			let filtered;
			if (this.difficulty && this.difficulty.toLowerCase() === "sale") {
				filtered = this.courses.filter(course => course.sale);
				filtered.sort((a, b) => b.sale - a.sale);
			} else if (["beginner", "advanced", "expert"].includes(this.difficulty.toLowerCase())) {
				filtered = this.courses.filter(course => {
					return course.difficultyLevel
						&& course.difficultyLevel.toLowerCase() === this.difficulty.toLowerCase();
				});
			} else {
				filtered = this.courses;
			}
			this.shuffle(filtered);
			filtered = filtered.slice(0, 6);
			for (const course of filtered) {
				if (course.image) {
					this.fetchImage(course);
				}
			}
			return filtered;
		},
		loopMode() {
			return this.filteredCourses.length > 3;
		},
		slidesPerView() {
			return Math.min(this.filteredCourses.length, 3);
		},
		aspectRatio() {
			return 4 / Math.min(Math.max(this.filteredCourses.length, 1), 3);
		}
	},
	methods: {
		intervalFunction() {
			this.$refs.carousel.swiper.slideNext();
		},
		resetSlideTimer() {
			clearInterval(this.interval);
			this.interval = setInterval(this.intervalFunction, this.autoSlideInterval);
		},
		async fetchImage(course) {
			const imageResponse = await fetch(this.$backendUrl + "images/" + course.image.imageId);

			const url = URL.createObjectURL(await imageResponse.blob());
			this.imageUrls.push(url);
			course.image.url = url;
		},
		shuffle(array) {
			//Fisher-Yates algorithm
			let i = array.length;
			while (i != 0) {
				let randomIndex = Math.floor(Math.random() * i);
				i--;
				[array[i], array[randomIndex]] = [array[randomIndex], array[i]]

			}
		}
	},
	data() {
		return {
			autoSlideInterval: 5000,
			interval: null,
			imageUrls: []
		}
	},
	mounted() {
		this.interval = setInterval(this.intervalFunction, this.autoSlideInterval);
	},
	unmounted() {
		clearInterval(this.interval);
		for (const url of this.imageUrls) {
			URL.revokeObjectURL(url);
		}
	},
}
</script>

<style scoped lang="scss">
.v-card {
	margin-top: 20px;
	margin-bottom: 20px;
}

swiper-container {
	width: calc(50vw + 100px);
}

swiper-slide {
	padding-left: 10px;
	padding-right: 10px;
	box-sizing: border-box;
}

.bullet-positioning-box {
	margin-bottom: 30px;
}

.no-image {
	aspect-ratio: v-bind(aspectRatio);
	width: 100%;
	display: flex;
	justify-content: center;
	flex-direction: column;

	h3 {
		color: rgb(var(--v-theme-weakText));
		font-weight: lighter;
	}
}
</style>
