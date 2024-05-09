<template>
  <v-app-bar class="toolbar-app" :elevation="0" id="app-bar">
    <div id="bar">
      <div id="topbar-left">
        <!--Look under! To use vuetify color, just set color to the variable you want to use-->
        <v-btn size="large" @click="this.$router.push('/')">Learniverse</v-btn>
      </div>
      
      <searchBar :lastGlobalClick="lastGlobalClick"></searchBar>
  
      <!-- Hidden, but kept for future reference -->
      <div id="topbar-right">
        <v-btn v-if="isLoggedIn" prepend-icon="mdi-account" stacked size="small" @click="navigateToAccount">View Account</v-btn>
        <v-btn v-if="isLoggedIn" prepend-icon="mdi-logout" stacked size="small" @click="handleLogout">Log Out</v-btn>
        <v-btn v-else prepend-icon="mdi-login" stacked size="small" @click="navigateToSignIn">Sign In</v-btn>
        <v-btn prepend-icon="mdi-heart" stacked size="small" @click="navigateToFavorites">View Favorites</v-btn>
      </div>
    </div>
    
  </v-app-bar>
  
</template>

<script>
import { computed } from "vue";
import { store } from '../utility/store';
import SearchBar from '@/components/SearchBar.vue';

export default {
  name: 'TopToolbar',
  setup() {
    const isLoggedIn = computed(() => store.user.isLoggedIn);
    return {
      user: store.user,
      isLoggedIn
    };
  },
  methods: {
    navigateToAccount() {
      this.$router.push('/account');
    },
    navigateToFavorites() {
      this.$router.push('/favorites');
    },
    navigateFrontPage() {
      this.$router.push('/');
    },
    navigateToSignIn() {
      this.$router.push('/login');
    },
    handleLogout() {
      store.logout();
      localStorage.removeItem('authToken');
      this.$router.push('/login');
    }
  },
  props: ['lastGlobalClick']
}
</script>

<style lang="scss" scoped>
#app-bar {
  /* Was not necessary to keep all the moz, opera etc as it is all supported by default now. */
  background-image: linear-gradient(to right, rgb(var(--v-theme-gradiantOne)), rgb(var(--v-theme-gradiantTwo)));
}

.v-btn {
  color: rgb(var(--v-theme-background));
}

.v-form {
  color: rgb(var(--v-theme-background));
}

.card {
  padding: 2em;
}

#content {
  flex-grow: 1;
}

#topbar-left, #topbar-right {
  display: flex;
  align-items: center;
}

#topbar-left {
  justify-content: flex-start;
  gap: 10px 20px;
}

#topbar-right {
  justify-content: flex-end;
  gap: 10px 20px;
}

#bar {
  display: grid;
  grid-auto-columns: minmax(0, 1fr);
  grid-auto-flow: column;
  width: 100%;
}

#app-bar {
  overflow: visible;
}
</style>