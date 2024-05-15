<template>
	<v-card>
		<v-card-title>
			<router-link :to="{path: 'search', query: {type: 'course', difficulty: difficulty.toLowerCase() == 'sale' ? 'featured' : difficulty.toLowerCase()}}">
				{{ difficulty.toLowerCase() == "sale" ? "Featured" : difficulty }} courses
			</router-link>
		</v-card-title>

		<swiper-container @swipernavigationprev="resetSlideTimer" @swipernavigationnext="resetSlideTimer" ref="carousel"
			:slides-per-view="slidesPerView" :loop="loopMode" css-mode="true" navigation="true" pagination="true">
			<swiper-slide v-for="course in filteredCourses">
				<div class="bullet-positioning-box">
					<router-link :to="'/course/' + course.id">
						<v-img alt="course.alt":src="course.image" :aspect-ratio="aspectRatio" cover></v-img>
						{{ course.name }}
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
		difficulty: String
	},
	computed: {
		filteredCourses() {
			let filtered;
			if (this.difficulty && this.difficulty.toLowerCase() == "sale") {
				filtered = this.courses.filter(course => course.sale);
				filtered.sort((a, b) => b.sale - a.sale);
			} else if (["beginner", "advanced", "expert"].includes(this.difficulty.toLowerCase())) {
				filtered = this.courses.filter(course => course.difficulty == this.difficulty); 
			} else {
				filtered = this.courses;
			}
			return [...filtered, ...filtered];
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
		}
	},
	data() {
		return {
			autoSlideInterval: 5000,
			interval: null
		}
	},
	mounted() {
		this.interval = setInterval(this.intervalFunction, this.autoSlideInterval);
	},
	unmounted() {
		clearInterval(this.interval);
	}
}
</script>

<style scoped>
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
</style>