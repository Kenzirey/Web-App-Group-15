<template>
	<v-container>
		<v-row>
			<v-col cols="12">
				<h1 class="text-h4 my-4">Course Provider Management</h1>
				<h2 class="text-h5 my-5" v-if="provider.courseProviderId !== null">Editing provider with ID: {{ provider.courseProviderId }}</h2>
				<v-btn @click="resetForm" v-if="provider.courseProviderId !== null">Cancel edit</v-btn>
			</v-col>
		</v-row>
		<v-form ref="form" @submit.prevent="handleSubmit">
			<v-row>
				<v-col cols="12" md="6">
					<v-text-field label="Provider Name" v-model="provider.providerName" required></v-text-field>
				</v-col>
				<v-col cols="12" md="6">
					<v-text-field label="Provider Website URL" v-model="provider.url" required></v-text-field>
				</v-col>
			</v-row>
			<v-card class="link-card">
				<h2 class="listings-header">
					Course Listings
				</h2>
				<v-row v-for="(link, index) in links">
					<v-col>
						<v-select label="Course" :items="courses" v-model="link.course" item-title="courseName" return-object required></v-select>
					</v-col>
					<v-col>
						<v-text-field label="Price" v-model="link.price" type="number" min="0" required></v-text-field>
					</v-col>
					<v-col>
						<v-select label="Currency" :items="$currencies.value" v-model="link.currency"></v-select>
					</v-col>
					<v-btn aria-label="Remove listing" class="listing-remove" icon="mdi-trash-can" @click="removeLink(index)"></v-btn>
				</v-row>
				<v-card-actions>
					<v-btn prepend-icon="mdi-plus" color="primary" @click="addEmptyLink">Add Listing</v-btn>
				</v-card-actions>
			</v-card>
			<v-row>
				<v-col cols="12">
					<v-btn color="primary" type="submit">Submit</v-btn>
				</v-col>
			</v-row>
		</v-form>

		<v-row>
			<v-col cols="12">
				<provider-list :providers="providers" @edit-provider="prepareProviderForEdit"
					@delete-provider="deleteProvider"></provider-list>
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
import ProviderList from '@/components/ProviderList.vue';

export default {
	name: 'AdminProviders',
	components: {
		ProviderList,
	},
	data() {
		return {
			providers: [],
			provider: {
				courseProviderId: null,
				providerName: '',
				url: '',
				courseProviderLinks: []
			},
			links: [{
				course: {
					courseName: "Test course",
					courseId: 1
				},
				price: 500,
				currency: "USD"
			}],
			courses: [],
			newCategoryName: '',
			snackbar: false,
			snackbarText: ''
		};
	},
	methods: {
		addEmptyLink() {
			this.links.push({course: null, price: null, currency: this.$currency.value});
		},
		removeLink(index) {
			this.links.splice(index, 1);
		},
		async handleSubmit() {
			if (!this.$refs.form.validate()) {
				this.snackbarText = 'Please fill all required fields.';
				this.snackbar = true;
				return;
			}

			const isAddingProvider = this.provider.courseProviderId === null;
			let url = this.$backendUrl + "providers";
			if (!isAddingProvider) {
				url += "/" + this.provider.courseProviderId;
			}
			const method = isAddingProvider ? "POST" : "PUT";
			const body = JSON.stringify({
				providerName: this.provider.providerName,
				url: this.provider.url
			});

			let response;
			try {
				response = await this.$authFetchOrPromptLogin(url, { method, body, headers: {'Content-Type': 'application/json'}});
			} catch (error) {
				console.error('Failed to submit provider:', error);
				this.snackbarText = `Failed to submit provider: ${error.response ? error.response.message : error.message}`;
				this.snackbar = true;
			}
			const id = isAddingProvider ? await response.json() : this.provider.courseProviderId;
			const persistentLinkIds = this.links.filter(link => link.id).map(link => link.course.courseId);
			const promises = [];
			for (const oldLink of this.provider.courseProviderLinks) {
				if (!persistentLinkIds.includes(oldLink.id.courseId)) {
					console.log("deleting");
					promises.push(this.$authFetchOrPromptLogin(`${this.$backendUrl}providers/${id}/coursePriceListings/${oldLink.id.courseId}`, { method: "DELETE" }));
				}
			}
			for (const newLink of this.links) {
				console.log(newLink);
				if (newLink.id && this.provider.courseProviderLinks.map(link => link.id.courseId).includes(newLink.id.courseId)) {
					console.log("putting");
					promises.push(this.$authFetchOrPromptLogin(`${this.$backendUrl}providers/${id}/coursePriceListings/${newLink.id.courseId}`, {
						method: "PUT",
						headers: { "Content-Type": "application/json" },
						body: JSON.stringify({ price: newLink.price, currency: newLink.currency })
					}));
				} else {
					console.log("posting");
					promises.push(this.$authFetchOrPromptLogin(`${this.$backendUrl}providers/${id}/coursePriceListings/${newLink.course.courseId}`, {
						method: "POST",
						headers: { "Content-Type": "application/json" },
						body: JSON.stringify({ price: newLink.price, currency: newLink.currency }),
						currency: newLink.currency
					}));
				}
			}
			Promise.all(promises).then(() => {
				this.snackbarText = isAddingProvider ? 'Provider added successfully.' : 'Provider updated successfully.';
				this.snackbar = true;
				this.fetchProviders();
				this.$refs.form.reset();
				this.resetForm();
			})
			
		},
		resetForm() {
			this.provider = {
				courseProviderId: null,
				providerName: '',
				url: '',
				courseProviderLinks: []
			};
			this.links = [];
		},
		addCourseToLink(link) {
			link.course = { courseId: link.id.courseId, courseName: this.courses.find(course => course.courseId === link.id.courseId).courseName};
			
		},
		async fetchProviders() {
			try {
				this.providers = await (await fetch(this.$backendUrl + "providers")).json();
			} catch (error) {
				console.error('Failed to fetch providers:', error);
			}
		},
		prepareProviderForEdit(provider) {
			this.provider = { ...provider };
			this.links = [...provider.courseProviderLinks];
			this.links.forEach(link => this.addCourseToLink(link));
		},
		async deleteProvider(providerId) {
			if (confirm("Are you sure you want to delete this provider?")) {
				try {
					const response = await this.$authFetchOrPromptLogin(`${this.$backendUrl}providers/${providerId}`, { method: "DELETE" });
					if (response.ok) {
						this.snackbarText = 'Provider successfully deleted.';
						this.snackbar = true;
						this.fetchProviders();
					}
				} catch (error) {
					console.error('Failed to delete provider:', error);
					this.snackbarText = 'Failed to delete provider.';
					this.snackbar = true;
				}
			}
		},
	},
	mounted() {
		fetch(this.$backendUrl + "courses").then(response => response.json()).then(courses => this.courses = courses);
		this.fetchProviders();
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

.link-card {
	padding: 20px;
}

.listings-header {
	font-weight: normal;
	margin-bottom: 10px;
	text-align: left;
}

.listing-remove {
	margin-top: 12px;
	margin-right: 6px;
}
</style>
