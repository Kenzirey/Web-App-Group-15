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
        <v-container>
          <v-row class="account-info-container">
            <v-col cols="12">
              <v-card outlined class="account-card">
                <v-card-title class="account-card-title"></v-card-title>
                <v-card-text>
                  <v-list dense>
                    <v-list-item>
                      <v-list-item-content>
                        <v-list-item-title>Name</v-list-item-title>
                        <v-list-item-subtitle>{{ user.name }}</v-list-item-subtitle>
                      </v-list-item-content>
                    </v-list-item>
                    <v-list-item>
                      <v-list-item-content>
                        <v-list-item-title>Email</v-list-item-title>
                        <v-list-item-subtitle>{{ user.email }}</v-list-item-subtitle>
                      </v-list-item-content>
                    </v-list-item>
                    <v-list-item v-if="user.roles.length">
                      <v-list-item-content>
                        <v-list-item-title>Role</v-list-item-title>
                        <v-list-item-subtitle>{{ user.roles.join(', ') }}</v-list-item-subtitle>
                      </v-list-item-content>
                    </v-list-item>
                  </v-list>
                </v-card-text>
                <v-card-actions>
                  <v-btn class="action-button" @click="changePassword">Change Password</v-btn>
                </v-card-actions>
              </v-card>
            </v-col>
          </v-row>
        </v-container>
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

.title {
  font-size: 30px;
  margin: 14px 0;
  text-align: center;
}

.account-info-container {
  display: flex;
  justify-content: center;
}

.account-card {
  padding: 16px;
  text-align: center;
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
  margin: 5px;
  text-transform: uppercase;
}

.v-btn--text {
  padding: 0 !important;
}
</style>
