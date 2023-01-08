<template>
  <div>
    <v-card class="ma-10 pa-5">
      <v-card-title>
        User List
        <v-spacer></v-spacer>

        <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="Search By Name"
          single-line
          hide-details
          clearable
        ></v-text-field>
        <v-btn color="primary" @click="onClickCreateBtn()" class="ml-2 mt-2">
          <v-icon left> mdi-plus </v-icon>
          Add
        </v-btn>
      </v-card-title>
      <v-data-table :headers="headers" :items="userList" :items-per-page="10">
        <template v-slot:item.role="{ item }">
          <v-chip
            label
            color="item?.role === 'admin' ? primary: success"
            v-model="item.role"
          >
            {{ item.role }}</v-chip
          >
        </template>
        <template v-slot:item.actions="{ item }">
          <!-- Edit User -->
          <v-btn
            class="mr-3"
            color="primary"
            fab
            x-small
            dark
            @click="onClickUpdateBtn(item)"
          >
            <v-icon>mdi-pencil</v-icon>
          </v-btn>

          <!-- Delete User -->
          <v-btn color="red" fab x-small dark @click="onClickDeleteBtn(item)">
            <v-icon>mdi-delete</v-icon>
          </v-btn>
        </template>
      </v-data-table>
    </v-card>
    <v-dialog v-model="createDialog" width="500">
      <v-card>
        <v-card-title>Create User</v-card-title>
        <v-card-text>
          <v-form ref="createUserForm" v-model="createUserForm">
            <v-text-field
              v-model="name"
              label="Name"
              placeholder="Enter user name"
              required
              :rules="[(v) => !!v || 'Required']"
            >
            </v-text-field>
            <v-text-field
              v-model="email"
              label="Email"
              placeholder="Enter email"
              required
              :rules="[
                (v) => !!v || 'Required',
                (v) => (v && /.+@.+\..+/.test(v)) || 'E-mail must be valid',
              ]"
            >
            </v-text-field>
            <v-text-field
              v-model="phoneNumber"
              label="Phone Number"
              placeholder="Enter phone number"
            >
            </v-text-field>
            <v-text-field
              v-model="password"
              :counter="10"
              :rules="[
                (v) => !!v || 'Required',
                (v) =>
                  (v && v.length <= 10) ||
                  'Password must be less than 10 characters',
              ]"
              :type="passwordShow ? 'text' : 'password'"
              :append-icon="passwordShow ? 'mdi-eye' : 'mdi-eye-off'"
              @click:append="passwordShow = !passwordShow"
              label="Password"
              required
            ></v-text-field>
            <v-radio-group v-model="role" row>
              <v-radio label="Admin" value="admin"></v-radio>
              <v-radio label="User" value="user"></v-radio>
            </v-radio-group>
          </v-form>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            @click="
              createDialog = false;
              name = '';
            "
            >Cancel</v-btn
          >
          <v-btn color="primary" dark @click="createUser()">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-dialog v-model="updateDialog" width="500">
      <v-card>
        <v-card-title>Update User</v-card-title>
        <v-card-text>
          <v-form ref="updateUserForm" v-model="updateUserForm">
            <v-text-field
              v-model="name"
              label="Name"
              placeholder="Enter user name"
              required
              :rules="[(v) => !!v || 'Required']"
            >
            </v-text-field>
            <v-text-field
              v-model="email"
              label="Email"
              placeholder="Enter email"
              required
              :rules="[
                (v) => !!v || 'Required',
                (v) => (v && /.+@.+\..+/.test(v)) || 'E-mail must be valid',
              ]"
            >
            </v-text-field>
            <v-text-field
              v-model="phoneNumber"
              label="Phone Number"
              placeholder="Enter phone number"
            >
            </v-text-field>
          </v-form>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            @click="
              updateDialog = false;
              name = '';
              email = '';
              phoneNumber = '';
              role = 'admin';
            "
            >Cancel</v-btn
          >
          <v-btn color="primary" dark @click="updateUser(item)">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-dialog v-model="deleteDialog" width="500">
      <v-card>
        <v-card-title>Delete User</v-card-title>
        <v-card-text> Are you sure to delete this? </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn @click="deleteDialog = false">Cancel</v-btn>
          <v-btn color="red" dark @click="deleteUser(selectItem.id)"
            >Delete</v-btn
          >
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>
  
  <script>
import http from "@/utils/http";

export default {
  name: "admin_users",

  data: () => {
    return {
      headers: [
        { text: "ID", value: "id", sortable: true },
        { text: "Name", value: "name", sortable: true },
        { text: "Email", value: "email", sortable: true },
        { text: "Phone Number", value: "phoneNumber", sortable: true },
        { text: "role", value: "role", sortable: false },
        { text: "Actions", value: "actions", sortable: false },
      ],
      search: "",
      userList: [],
      selectItem: {},

      createDialog: false,
      updateDialog: false,
      deleteDialog: false,

      createUserForm: false,
      updateUserForm: false,

      toUpdateId: 0,
      name: "",
      email: "",
      phoneNumber: "",
      role: "admin",
      password: "",
      passwordShow: false,
    };
  },
  async created() {
    await this.fetchUsers();
  },

  methods: {
    async fetchUsers() {
      const res = await http.get(
        "/api/admin/users",
        this.search
          ? {
              name: this.search,
            }
          : null
      );
      if (res && res.status === 200) {
        const data = await res.json();
        if (data) {
          this.userList = data;
        }
      }
    },

    async createUser() {
      if (this.$refs.createUserForm.validate()) {
        const res = await http.post("/api/admin/users/create", {
          name: this.name,
          email: this.email,
          phoneNumber: this.phoneNumber,
          role: this.role,
          password: this.password,
        });

        if (res && res.status === 200) {
          await this.fetchUsers();
          this.createDialog = false;
          this.name = "";
          this.email = "";
          this.phoneNumber = "";
          this.role = "admin";
          this.password = "";
        }
      }
    },

    async updateUser() {
      if (this.$refs.updateUserForm.validate()) {
        const res = await http.put(
          `/api/admin/users/update/${this.toUpdateId}`,
          {
            name: this.name,
            email: this.email,
            phoneNumber: this.phoneNumber,
            role: this.role,
            password: this.password,
            id: this.toUpdateId,
          }
        );

        if (res && res.status === 200) {
          await this.fetchUsers();
          this.updateDialog = false;
          this.name = "";
          this.email = "";
          this.phoneNumber = "";
          this.role = "admin";
          this.password = "";
        }
      }
    },

    async deleteUser(id) {
      const res = await http.del(`/api/admin/users/delete/${id}`);
      if (res && res.status === 200) {
        await this.fetchUsers();
        this.deleteDialog = false;
      }
    },

    onClickCreateBtn() {
      this.createDialog = true;
    },

    onClickUpdateBtn(item) {
      this.updateDialog = true;
      this.toUpdateId = item.id;
      this.name = item.name;
      this.email = item.email;
      this.phoneNumber = item.phoneNumber;
      this.password = item.password;
      this.role = item.role;
    },

    onClickDeleteBtn(item) {
      this.deleteDialog = true;
      this.selectItem = item;
    },
  },
};
</script>