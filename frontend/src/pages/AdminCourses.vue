<template>
	<v-container>
		<v-row>
			<v-col cols="12">
				<h1 class="text-h4 my-4">Course Management</h1>
				<h2 class="text-h5 my-5" v-if="course.courseId !== null">Editing course with ID: {{ course.courseId }}</h2>
				<v-btn @click="resetForm" v-if="course.courseId !== null">Cancel edit</v-btn>
			</v-col>
		</v-row>
		<v-form ref="form" @submit.prevent="handleSubmit">
			<v-row>
				<v-col cols="12" md="6">
					<v-text-field label="Course Name" v-model="course.courseName" required></v-text-field>
				</v-col>
				<v-col cols="12" md="6">
					<v-textarea label="Course Description" v-model="course.courseDescription" required></v-textarea>
				</v-col>
				<v-col cols="12" sm="6">
					<v-text-field label="Start Date" aria-label="Start Date" v-model="course.startDate" type="date" required></v-text-field>
				</v-col>
				<v-col cols="12" sm="6">
					<v-text-field label="End Date" aria-label="End Date" v-model="course.endDate" type="date" required></v-text-field>
				</v-col>
				<v-col cols="12" sm="4">
					<v-text-field label="Credits" aria-label="Credits" v-model="course.courseCredits" type="number" required></v-text-field>
				</v-col>
				<v-col cols="12" sm="4">
					<v-text-field label="Hours per Week" aria-label="Hours per week" v-model="course.hoursPerWeek" type="number"
						required></v-text-field>
				</v-col>
				<v-col cols="12" sm="4">
					<v-select label="Difficulty Level" v-model="course.difficultyLevel"
						:items="['Beginner', 'Intermediate', 'Advanced']" required></v-select>
				</v-col>
				<v-col cols="12" md="6">
					<v-text-field label="Related Certification" v-model="course.relatedCertification"></v-text-field>
				</v-col>
				<v-col cols="12" md="6">
					<v-select label="Category" v-model="course.categoryIds" :items="categories" item-text="name"
						item-value="id" multiple chips small-chips required></v-select>
				</v-col>
				<v-col cols="12">
					<v-file-input label="Upload Image" @change="handleImageUpload" prepend-icon="mdi-camera"
						accept="image/*" :disabled="course.courseId !== null && removeImage"></v-file-input>
					<v-checkbox label="Remove Image" v-model="removeImage" v-if="course.courseId !== null"></v-checkbox>
				</v-col>
			</v-row>
			<v-row>
				<v-col cols="12">
					<v-btn color="primary" type="submit">Submit</v-btn>
				</v-col>
			</v-row>
		</v-form>

		<!-- Form to add a new category -->
		<v-form ref="categoryForm" @submit.prevent="handleCategorySubmit">
			<v-row>
				<v-col cols="12" md="6">
					<v-text-field label="New Category Name" v-model="newCategoryName" required></v-text-field>
				</v-col>
				<v-col cols="12">
					<v-btn color="primary" type="submit">Add Category</v-btn>
				</v-col>
			</v-row>
		</v-form>

		<v-row>
			<v-col cols="12">
				<course-list :courses="courses" @edit-course="prepareCourseForEdit"
					@delete-course="deleteCourse"></course-list>
			</v-col>
		</v-row>

		<!-- Snackbar for notifications -->
		<v-snackbar v-model="snackbar" bottom right :timeout="3000">
			{{ snackbarText }}
			<v-btn color="red" text @click="snackbar = false">Close</v-btn>
		</v-snackbar>
	</v-container>
</template>

<script>
import axios from 'axios';
import { getCookie } from '../utility/cookieHelper';
import CourseList from '@/components/CourseList.vue';

export default {
	name: 'AdminCourses',
	components: {
		CourseList,
	},
	data() {
		return {
			courses: [],
			course: {
				courseId: null,
				courseName: '',
				difficultyLevel: '',
				startDate: '',
				endDate: '',
				courseCredits: 0,
				hoursPerWeek: 0,
				relatedCertification: '',
				courseDescription: '',
				categoryIds: [],
				imageId: null
			},
			removeImage: false,
			uploadedImage: null,
			categories: [],
			newCategoryName: '',
			snackbar: false,
			snackbarText: ''
		};
	},
	methods: {
		handleImageUpload(event) {
			this.uploadedImage = event.target.files[0];
		},
		async handleSubmit() {
			if (!this.$refs.form.validate()) {
				this.snackbarText = 'Please fill all required fields.';
				this.snackbar = true;
				return;
			}

			let payload;
			const headers = {
				Authorization: `Bearer ${getCookie('authToken')}`,
			};

			const isAddingCourse = this.course.courseId === null;
			let url = `${this.$backendUrl}courses`;
			let method = 'post';

			let imageId;
			if (this.removeImage) {
				imageId = null;
			} else if (this.uploadedImage) {
				const imgPayload = new FormData();
				imgPayload.append("image", this.uploadedImage);
				imageId = await (await this.$authFetchOrPromptLogin(this.$backendUrl + "images", {method: "POST", body: imgPayload})).json();
			} else {
				imageId = this.course.imageId;
			}
			payload = {
				courseName: this.course.courseName,
				difficultyLevel: this.course.difficultyLevel,
				startDate: this.course.startDate,
				endDate: this.course.endDate,
				courseCredits: this.course.courseCredits,
				hoursPerWeek: this.course.hoursPerWeek,
				relatedCertification: this.course.relatedCertification,
				courseDescription: this.course.courseDescription,
				categoryIds: this.course.categoryIds,
				imageId: imageId
			};

			if (!isAddingCourse) {
				url = `${this.$backendUrl}courses/${this.course.courseId}`;
				method = 'put';
			}

			try {
				const response = await axios({
					method: method,
					url: url,
					data: payload,
					headers: headers
				});
				this.snackbarText = isAddingCourse ? 'Course added successfully.' : 'Course updated successfully.';
				this.snackbar = true;
				this.fetchCourses();
				this.$refs.form.reset();
				this.resetForm();
			} catch (error) {
				console.error('Failed to submit course:', error);
				this.snackbarText = `Failed to submit course: ${error.response ? error.response.data.message : error.message}`;
				this.snackbar = true;
			}
		},



		async handleCategorySubmit() {
			if (!this.newCategoryName) return;
			const authToken = getCookie('authToken');
			try {
				await axios.post(
					`${this.$backendUrl}categories`,
					{ name: this.newCategoryName },
					{ headers: { Authorization: `Bearer ${authToken}` } }
				);
				this.fetchCategories();
				this.newCategoryName = '';
				this.snackbarText = 'Category added successfully.';
				this.snackbar = true;
			} catch (error) {
				console.error('Failed to add category:', error);
				this.snackbarText = `Failed to add category: ${error.response ? error.response.data.message : error.message}`;
				this.snackbar = true;
			}
		},
		async fetchCategories() {
			const authToken = getCookie('authToken');
			try {
				const response = await axios.get(`${this.$backendUrl}categories`, {
					headers: { Authorization: `Bearer ${authToken}` },
				});
				this.categories = response.data.map((cat) => ({ id: cat.categoryId, name: cat.categoryName }));
			} catch (error) {
				console.error('Failed to fetch categories:', error);
			}
		},
		resetForm() {
			this.course = {
				courseId: null,
				courseName: '',
				difficultyLevel: '',
				startDate: '',
				endDate: '',
				courseCredits: 0,
				hoursPerWeek: 0,
				relatedCertification: '',
				courseDescription: '',
				categoryIds: [],
				imageId: null,
			};
		},
		async fetchCourses() {
			const authToken = getCookie('authToken');
			try {
				const response = await axios.get(`${this.$backendUrl}courses`, {
					headers: { Authorization: `Bearer ${authToken}` },
				});
				this.courses = response.data;
			} catch (error) {
				console.error('Failed to fetch courses:', error);
			}
		},
		prepareCourseForEdit(course) {
			this.course = { ...course };
			this.course.imageId = course.image ? course.image.imageId : null;
			this.course.courseId = course.courseId;
			if (course.startDate) {
				this.course.startDate = course.startDate.split('T')[0];
			}
			if (course.endDate) {
				this.course.endDate = course.endDate.split('T')[0];
			}
		},
		async deleteCourse(courseId) {
			if (confirm("Are you sure you want to delete this course?")) {
				try {
					const response = await axios.delete(`${this.$backendUrl}courses/${courseId}`, {
						headers: { Authorization: `Bearer ${getCookie('authToken')}` },
					});
					if (response.status === 200 || response.status === 204) {
						this.snackbarText = 'Course successfully deleted.';
						this.snackbar = true;
						this.fetchCourses();
					}
				} catch (error) {
					console.error('Failed to delete course:', error);
					this.snackbarText = 'Failed to delete course.';
					this.snackbar = true;
				}
			}
		},
	},
	mounted() {
		this.fetchCourses();
		this.fetchCategories();
	},
};
</script>

<style scoped>
.v-container {
	max-width: 960px;
	margin: auto;
}

.v-btn {
	text-transform: none;
}
</style>
