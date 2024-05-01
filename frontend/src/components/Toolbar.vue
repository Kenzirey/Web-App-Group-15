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
        <v-btn prepend-icon="mdi-account" stacked size="small" @click="this.$router.push('/account')">View Account</v-btn>
        <v-btn prepend-icon="mdi-heart" stacked size="small" @click="this.$router.push('/favorites')">View Favorites</v-btn>
      </div>
    </div>
    
  </v-app-bar>
  
</template>

<script>
import SearchBar from '@/components/SearchBar.vue';

export default {
  name: 'TopToolbar',
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
  gap: 14px 26px;
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