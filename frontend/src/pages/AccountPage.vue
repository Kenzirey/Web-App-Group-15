<template>
	<v-container fluid class="pa-0 fill-height">
		<v-row class="fill-height" no-gutters>
			<v-col cols="12">
				<!-- Account Page Title -->
				<v-row class="title-container">
					<v-col>
						<h1 class="title">Account Details</h1>
					</v-col>
				</v-row>

				<!-- Account Information -->
				<section class="Account-information">
					<v-row class="account-info-container" aria-label="row container">
						<v-col cols="12">
							<v-card outlined class="account-card" aria-label="account-card">
								<v-card-title class="account-card-title"></v-card-title>
								<v-card-text>
									<v-list dense aria-label="account-list-items">
										<v-list-item class="info-item">
											<span class="item-label">Name: </span>
											<span class="item-value">{{ user.name }}</span>
										</v-list-item>
										<v-list-item class="info-item" aria-label="Email-container">
											<span class="item-label">Email: </span>
											<span class="item-value">{{ user.email }}</span>
										</v-list-item>
										<v-list-item v-if="user.roles.length" class="info-item"
																 aria-label="Role-container">
											<span class="item-label">Role: </span>
											<span class="item-value">{{ user.roles.join(', ') }}</span>
										</v-list-item>
									</v-list>
								</v-card-text>
								<v-card-actions class="card-actions">
									<v-btn class="action-button" @click="changePassword"
										 aria-label="Change Password Button">Change Password</v-btn>
									 <v-btn class="action-button" @click="() => this.$router.push('/admin')"
										v-if="user.roles.includes('ROLE_ADMIN')">Admin Dashboard</v-btn>
								</v-card-actions>
							</v-card>
						</v-col>
					</v-row>
				</section>
			</v-col>
		</v-row>
	</v-container>
</template>


<script>
import { computed } from "vue";
import { useRouter } from 'vue-router';
import { store } from '../utility/store';

export default {
	name: 'AccountPage',
	setup() {
		const user = computed(() => store.user);
		const router = useRouter();

		const changePassword = () => {
			router.push('/change-password');
		};

		return {
			user,
			changePassword
		};
	}
}
</script>


<style scoped lang="scss">
.title-container {
	display: flex;
	justify-content: center;
	margin-bottom: 20px;
}

.info-item {
	display: flex;
	align-items: center;
}

.label-container {
	display: flex;
	align-items: center;
	width: 100%;
}

.item-label {
	margin-right: 10px;
	font-weight: bold;
}

.item-value {
	flex-grow: 1;
	text-align: left;
}

.item-value,
.item-label {
	font-size: 18px;
}

.title {
	text-align: center;
}

.account-info-container {
	display: flex;
	justify-content: center;
	min-width: 400px;
}

.card-actions {
	display: flex;
	justify-content: center;
}

.account-card-title {
	margin-bottom: 16px;
	font-size: 20px;
}

.action-button {
	background-image: linear-gradient(to right, rgb(var(--v-theme-gradiantOne)), rgb(var(--v-theme-gradiantTwo)));
	color: rgb(var(--v-theme-background)) !important;
	font-size: 14px;
	font-weight: 500;
	margin-bottom: 5px;
	text-transform: uppercase;
}

.v-btn--text {
	padding: 0 !important;
}

@media (max-width: 600px) {
	h1 {
		font-size: 1.9em;
	}

	.account-info-container {
		display: flex;
		justify-content: center;
		min-width: 300px;
		width: 100%;
	}

	.item-label {
		font-size: 16px;
	}

	.item-value {
		font-size: 14px;
	}
}
</style>
