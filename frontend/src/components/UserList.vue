<template>
  <div class="user-list-container">
    <div class="search-container">
      <input
        type="text"
        v-model="searchQuery"
        @input="filterList"
        placeholder="Search"
        class="search-input"
      />
    </div>
    <h2>Users</h2>
    <ul class="user-list">
      <li v-for="user in filteredUsers" :key="user.id" class="user-item">
        <span>{{ user.name }} - 2FA: {{ user.twoFactorEnabled ? 'Enabled' : 'Disabled' }}</span>
        <div class="user-actions">
          <button @click="$emit('edit-user', user)" class="edit-user-btn">Edit</button>
          <button @click="$emit('delete-user', user.id)" class="delete-user-btn">Delete</button>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'UserList',
  props: {
    users: Array,
  },
  data() {
    return {
      searchQuery: '',
    };
  },
  computed: {
    filteredUsers() {
      return this.users.filter(user =>
        user.name.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    },
  },
  methods: {
    filterList() {
    },
  },
};
</script>

<style scoped>
.user-list-container {
  max-width: 600px;
  margin: 20px auto;
}

.search-container {
  margin-bottom: 1rem;
}

.search-input {
  width: 100%;
  padding: 0.5rem;
  border-radius: 4px;
  border: 1px solid #ccc;
}

.user-list {
  list-style-type: none;
  padding: 0;
}

.user-item {
  background: #fff;
  padding: 1rem;
  margin-bottom: 1rem;
  border-radius: 4px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.user-item:last-child {
  margin-bottom: 0;
}

.edit-user-btn,
.delete-user-btn {
  background-color: #f0ad4e;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 4px;
  cursor: pointer;
  color: white;
  transition: background-color 0.2s;
}

.edit-user-btn:hover,
.delete-user-btn:hover {
  background-color: #ec971f;
}

.delete-user-btn {
  background-color: #d9534f;
}

.delete-user-btn:hover {
  background-color: #c9302c;
}

.edit-user-btn:not(:last-child),
.delete-user-btn:not(:last-child) {
  margin-right: 0.5rem;
}
</style>
