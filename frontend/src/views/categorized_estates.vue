<template>
  <section_wrapper
    title="Categorized properties"
    subtitle="With Yumekaoku, everything related to finding an apartment can be done online"
  >
    <v-row dense style="padding: 0 50px">
      <v-col v-for="estate in estateData" :key="estate.id" cols="3">
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
  </section_wrapper>
</template>

<script>
import utils from "@/utils/utils";
import estate_card from "@/components/estate_card.vue";
import section_wrapper from "@/components/section_wrapper.vue";

export default {
  name: "categorized_estates",
  components: { estate_card, section_wrapper },

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