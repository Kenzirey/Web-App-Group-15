<template>
  <v-pagination  :total-visible="6" v-model="page" :length="Math.ceil(items.length / items_per_page)"></v-pagination>
  <v-data-iterator :items-per-page="items_per_page" :items="items" :page="page">
    <template v-slot:default="{ items }">
      <template
        v-for="(item, i) in items"
        :key="i"
      >
        <v-card v-bind="item.raw"></v-card>

        <br>
      </template>
    </template>
  </v-data-iterator>
  <v-pagination  :total-visible="6" v-model="page" :length="Math.ceil(items.length / items_per_page)"></v-pagination>
</template>

<script>
  import { ref } from 'vue';
  export default {
    setup() {
      const page = ref(1);
      const items_per_page = 20;
      const items = Array.from({ length: 250 }, (k, v) => ({
        title: 'Search result ' + (v + 1),
        text: 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Commodi, ratione debitis quis est labore voluptatibus! Eaque cupiditate minima, at placeat totam, magni doloremque veniam neque porro libero rerum unde voluptatem!',
      }));
      return {page, items_per_page, items};
    },
    watch: {
      page() {
        window.scrollTo({ top: 0});
      }
    },
    mounted() {
      const backend_base_url = "http://localhost:8080/";

      if (this.$route.query.q) {
        if (this.$route.query.type != "providers") {
          fetch(backend_base_url + `courses/search/${this.$route.query.q}`);
        }
        if (this.$route.query.type != "courses") {
          fetch(backend_base_url + `providers/search/${this.$route.query.q}`);
        }
      }
    }
  }
</script>