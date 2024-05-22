<template>
  <div class="provider-list">
    <div class="search-container">
      <input type="text" v-model="searchQuery" @input="filterList" placeholder="Search" class="search-input" />
    </div>
    <h2>Providers</h2>
    <table class="provider-table">
      <thead>
        <tr>
          <th>Provider Name</th>
          <th>Website URL</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="provider in filteredProviders" :key="provider.providerId">
          <td>{{ provider.providerName }}</td>
          <td>{{ provider.url }}</td>
          <td>
            <v-btn color="primary" @click="onEditProvider(provider)">Edit</v-btn>
            <v-btn color="error" @click="onDeleteProvider(provider.providerId)">Delete</v-btn>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  name: 'ProviderList',
  props: {
    providers: Array,
  },
  data() {
    return {
      searchQuery: '',
    };
  },
  computed: {
    filteredProviders() {
      return this.providers.filter(provider =>
        provider.providerName.toLowerCase().includes(this.searchQuery.toLowerCase())
        || (provider.url && provider.url.toLowerCase().includes(this.searchQuery.toLowerCase()))
      );
    },
  },
  methods: {
    formatDate(date) {
      return new Date(date).toLocaleDateString();
    },
    onEditProvider(provider) {
      this.$emit('edit-provider', provider);
    },
    onDeleteProvider(providerId) {
      this.$emit('delete-provider', providerId);
    },
  },
};
</script>

<style scoped>
:root {
  --border-color: #ddd;
}

.provider-list {
  margin-top: 20px;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.provider-table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 20px;
}

th, td {
  border: 1px solid var(--border-color);
  padding: 12px;
  text-align: left;
}

.search-container {
  margin-bottom: 20px;
}

.search-input {
  width: 100%;
  padding: 10px;
  border-radius: 4px;
  border: 1px solid #ccc;
  font-size: 16px;
}

.v-btn {
  padding: 8px 16px;
  margin-right: 10px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.2s;
}
</style>
