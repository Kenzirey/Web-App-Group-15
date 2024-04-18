<template>
  <v-container class="user-list-container">
    <v-row>
      <v-col cols="12">
        <v-text-field
          v-model="searchQuery"
          @input="filterList"
          label="Search"
          single-line
          hide-details
          class="mb-4"
        ></v-text-field>
      </v-col>
    </v-row>

    <v-row>
      <v-col cols="12">
        <v-row align="center" justify="start" no-gutters>
          <v-col class="text-h6 py-2">Users</v-col>
        </v-row>
        <v-list>
          <v-list-item-group>
            <v-list-item
              v-for="user in filteredUsers"
              :key="user.id"
              class="user-list-item"
            >
              <v-row align="center" justify="space-between" no-gutters>
                <v-col cols="8">
                  <div class="user-info">
                    <v-list-item-title>{{ user.name }}</v-list-item-title>
                    <v-list-item-subtitle
                      :class="{'text-green': user.twoFactorEnabled, 'text-red': !user.twoFactorEnabled}"
                    >
                      2FA: {{ user.twoFactorEnabled ? 'Enabled' : 'Disabled' }}
                    </v-list-item-subtitle>
                  </div>
                </v-col>
                <v-col cols="4" class="d-flex justify-end">
                  <v-list-item-action class="user-actions">
                    <v-btn icon @click="$emit('edit-user', user)" color="orange">
                      <v-icon>mdi-pencil</v-icon>
                    </v-btn>
                    <v-btn icon @click="$emit('prepare-delete-user', user.id)" color="red">
                      <v-icon>mdi-delete</v-icon>
                    </v-btn>
                  </v-list-item-action>
                </v-col>
              </v-row>
            </v-list-item>
          </v-list-item-group>
        </v-list>
      </v-col>
    </v-row>
  </v-container>
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

.user-list-item .user-info {
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.user-list-item .user-actions {
  display: flex;
  justify-content: flex-end;
}

.text-h6 {
  font-size: 1.25rem;
  font-weight: normal;
}

.text-green {
  color: #08f714;
  font-weight: bold;
}

.text-red {
  color: #fb0101;
  font-weight: bold;
}
</style>

