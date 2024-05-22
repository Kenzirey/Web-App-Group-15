<!-- src/pages/HomePage.vue -->
<template>

	<div class="home-page-container">
		<section id="introduction">
			<h1>Welcome to Learniverse Connect</h1>
		</section>

		<!-- Any other sections we want on the homepage -->
		<section class="Carousel test">
			<CourseCarousel :courses="courses" difficulty="Sale"/>
			<CourseCarousel :courses="courses" difficulty="Beginner"/>
			<CourseCarousel :courses="courses" difficulty="Intermediate"/>
			<CourseCarousel :courses="courses" difficulty="Expert"/>
		</section>
	</div>
</template>

<script>

/**
 * Carousel component allows us to add multiple carousels with reusable code.
 * We just need to change its category, sale, or its difficulty. We can expand on whatever we want.
 */
import CourseCarousel from '@/components/CourseCarousel.vue';

export default {
	name: 'HomePage',
	components: {
		CourseCarousel
	},
	data() {
		return {
			courses: []
		}
	},
	methods: {
		async fetchData() {
			const courseResponse = await fetch(this.$backendUrl + "courses")
			if (courseResponse.ok) {
				this.courses = await courseResponse.json();
			}
		}

	},
	created() {
		this.fetchData();
	}
}
</script>

<style scoped>
.carousel-test {
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
}

.home-page-container {
	align-items: center;
	display: flex;
	flex-direction: column;
}

/* These media sizes and comments were taken from:
 https://mediag.com/blog/popular-screen-resolutions-designing-for-all/
 */
/* start of medium tablet styles, if we want it */
@media screen and (max-width: 767px) {
	#introduction {
		margin: auto 4px auto 4px;
	}

	h1 {
		font-size: 1.6em;
	}

}

/* start of phone styles */
@media screen and (max-width: 479px) {
	#introduction {
		margin: auto 4px auto 4px;
	}

	p {
		margin-top: 5px;
	}

}

</style>
