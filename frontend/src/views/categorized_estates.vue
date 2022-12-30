<template>
  <v-row dense>
    <v-col v-for="estate in estateData" :key="estate.id">
      <estate_card
        :id="estate.id"
        :title="estate.title"
        :location="estate.location"
        :rentFee="estate.rentFee"
        :category="estate.category"
        :imagePath="estate.imagePath"
      ></estate_card>
    </v-col>
  </v-row>
</template>

<script>
import utils from "@/utils/utils";
import estate_card from "@/components/estate_card.vue";

export default {
  name: "categorized_estates",
  components: { estate_card },

  data: () => ({ estateData: [] }),

  async created() {
    await this.fetchEstatesByCategory();
  },

  methods: {
    async fetchEstatesByCategory() {
      const res = await utils.http.get(
        `/api/categories/${this.$route.params.categoryId}/estates`
      );
      if (res && res.status === 200) {
        const data = await res.json();
        if (data) {
          this.estateData = data;
        }
      }
    },
  },
};
</script>