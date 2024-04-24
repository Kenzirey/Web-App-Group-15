<template>
	<v-card>
		<v-card-title>{{ title }}</v-card-title>

		<swiper-container @swipernavigationprev="resetSlideTimer" @swipernavigationnext="resetSlideTimer" ref="carousel"
			:slides-per-view="slidesPerView" loop="true" css-mode="true" navigation="true" pagination="true">
			<swiper-slide v-for="course in filteredCourses">
				<div style="padding-bottom: 8%;">
					<v-img :src="course.image" :aspect-ratio="aspectRatio" cover></v-img>
					<div class="course-name">{{ course.name }}</div>
				</div>
			</swiper-slide>
		</swiper-container>
	</v-card>

</template>

<style scoped>
swiper-container {
	width: calc(50vw + 100px);
}
</style>

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
		title: String,
		courses: Array,
		difficulty: String,
		category: String,
		onSale: Boolean,
	},
	computed: {
		filteredCourses() {
			/**
			 * Filters the courses, can filter based on only difficulty, category OR both.
			 * Easily expandable by just creating a new const and following the pattern.
			 */
			return this.courses.filter(course => {
				const matchesDifficulty = this.difficulty ? course.difficulty === this.difficulty : true;
				const matchesCategory = this.category ? course.category === this.category : true;
				const matchesSale = this.onSale ? course.onSale === this.onSale : true;
				return matchesDifficulty && matchesCategory && matchesSale;
			});
		},
		slidesPerView() {
			return this.filteredCourses.length == 1 ? 1 : 2;
		},
		aspectRatio() {
			return this.filteredCourses.length == 1 ? 2.667 : 1.333;
		}
	},
	methods: {
		intervalFunction() {
			this.$refs.carousel.swiper.slideNext();
		},
		resetSlideTimer() {
			clearInterval(this.interval);
			this.interval = setInterval(this.intervalFunction, this.autoSlideInterval)
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
