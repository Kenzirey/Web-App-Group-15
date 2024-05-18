<template>
  <v-app-bar class="toolbar-app" :elevation="0" id="app-bar">
    <!--Look under! To use vuetify color, just set color to the variable you want to use-->
      <v-btn id="home-button-desktop" class="desktop-tablet" @click="this.$router.push('/')" aria-label="Home">Learniverse</v-btn>
      <v-btn id="home-button-mobile" class="mobile" @click="this.$router.push('/')" icon="mdi-home" aria-label="Homet"></v-btn>
    <searchBar id="search-bar" :lastGlobalClick="lastGlobalClick"></searchBar>

    <!-- Hidden, but kept for future reference -->
    <template v-slot:append>
      <div class="desktop" id="toolbar-icons">
        <v-btn v-if="isLoggedIn" prepend-icon="mdi-account" stacked size="small" @click="navigateToAccount" aria-label="View Account">View
          Account</v-btn>
        <v-btn v-if="isLoggedIn" prepend-icon="mdi-logout" stacked size="small" @click="handleLogout" aria-label="Log out">Log Out</v-btn>
        <v-btn v-else prepend-icon="mdi-login" stacked size="small" @click="navigateToSignIn" aria-label="Sign in">Sign In</v-btn>
        <v-btn prepend-icon="mdi-heart" stacked size="small" @click="navigateToFavorites" aria-label="View Favorites">View Favorites</v-btn>
      </div>
      <div class="tablet-mobile" id="toolbar-icons">
        <v-btn v-if="isLoggedIn" icon="mdi-account" @click="navigateToAccount" aria-label="View Account"></v-btn>
        <v-btn v-if="isLoggedIn" icon="mdi-logout" @click="handleLogout" aria-label="Log out"></v-btn>
        <v-btn v-else icon="mdi-login" @click="navigateToSignIn" aria-label="Sign in"></v-btn>
        <v-btn icon="mdi-heart" @click="navigateToFavorites" aria-label="View Favorites"></v-btn>
      </div>
    </template>

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

.toolbar-app {
  overflow: visible;
}

#toolbar-icons {
  white-space: nowrap;
}

.tablet, .mobile, .tablet-mobile {
  display: none;
}

#search-bar {
  width: min(600px, calc(100vw - 530px));
}

#home-button-mobile {
  margin: 0 10px;
}
#home-button-desktop {
  margin: 0 10px;
}

/* start of medium tablet styles, if we want it */
@media screen and (max-width: 768px) {
  .desktop, .desktop-mobile {
    display: none;
  }

  .tablet, .tablet-mobile {
    display: block;
  }

  #search-bar {
    width: calc(100vw - 330px);
  }
}

/* start of phone styles */
@media screen and (max-width: 480px) {
  .tablet, .desktop-tablet {
    display: none;
  }

  .mobile, .desktop-mobile {
    display: block;
  }

  #search-bar {
    width: calc(100vw - 248px);
  }
}

</style>