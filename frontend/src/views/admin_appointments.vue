<template>
  <v-card class="ma-10 pa-5">
    <v-card-title>
      Appointment List
      <v-spacer></v-spacer>
    </v-card-title>
    <v-data-table :headers="headers" :items="datalist" :items-per-page="10">
      <template v-slot:item.user="{ item }">
        {{ item?.user?.name }} - {{ item?.user?.phoneNumber }}
        <br />
        <small>
          {{ item?.user?.email }}
        </small>
      </template>
      <template v-slot:item.estate="{ item }">
        <router-link to="{path: '/estates/' + item?.estate?.id}">{{
          item?.estate?.title
        }}</router-link>
      </template>
    </v-data-table>
  </v-card>
</template>

<script>
import utils from "@/utils/utils";

export default {
  name: "admin_appointments",

  data: () => ({
    headers: [
      { text: "User", value: "user", sortable: false },
      { text: "Estate", value: "estate", sortable: false },
      { text: "Appointment Date", value: "appointmentDate", sortable: false },
      // { text: "Status", value: "status", sortable: false },
      { text: "Message", value: "message", sortable: false },
    ],
    datalist: [],
    // toUpdateId: 0,
  }),

  async created() {
    await this.fetchAppointments();
  },

  methods: {
    async fetchAppointments() {
      const res = await utils.http.get("/api/admin/appointments");
      if (res && res.status === 200) {
        const data = await res.json();
        if (data) {
          this.datalist = data;
        }
      }
    },

    // async updateAppointmentStatus() {
    //   const res = await utils.http.put(
    //     `/api/admin/appointments/update/${this.toUpdateId}/status`,
    //     {
    //       status: this.status,
    //       id: this.toUpdateId,
    //     }
    //   );

    //   if (res && res.status === 200) {
    //     await this.fetchAppointments();
    //   }
    // },
  },
};
</script>


