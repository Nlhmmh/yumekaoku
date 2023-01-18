<template>
  <div>
    <div class="landing">
      <div class="advanced-form">
        <h2 style="color: white" class="my-2">
          Find a home in Japan with Yumekaoku
        </h2>

        <div class="d-flex">
          <v-text-field
            style="width: 350px"
            v-model="search"
            solo
            label="Search by location"
            clearable
            class="mx-1"
          ></v-text-field>
          <v-select
            v-model="category"
            :items="categories"
            label="Property Category"
            item-text="name"
            item-value="id"
            solo
            class="mx-1"
          ></v-select>

          <!-- :disabled="!search || categoryId === 0" -->
          <v-btn
            color="success"
            class="mx-1"
            large
            @click="onSearch()"
            style="height: 46px"
          >
            Search
            <v-icon right>mdi-magnify</v-icon>
          </v-btn>
        </div>
      </div>
    </div>
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
  </div>
</template>

<script>
import utils from "@/utils/utils";
import estate_card from "@/components/estate_card.vue";
import section_wrapper from "@/components/section_wrapper.vue";

export default {
  name: "categorized_estates",
  components: { estate_card, section_wrapper },

  data: () => ({ estateData: [], search: "", category: "all", categories: [] }),

  async created() {
    await this.fetchEstatesByCategory();
    await this.fetchCategories();
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

    async fetchCategories() {
      const res = await utils.http.get("/api/categories");
      if (res && res.status === 200) {
        const data = await res.json();
        if (data) {
          this.categories = data;
        }
      }
    },

    onSearch() {
      this.$router.push({
        path: "/estates",
        query: {
          search: this.search ?? "",
          category: this.category,
        },
      });
    },
  },
};
</script>

<style scoped>
.landing {
  width: 100%;
  height: 70vh;
  background: url("/src/assets/landing.jpg") center center no-repeat;
  background-size: cover;
  display: flex;
  justify-content: center;
  align-items: center;
}
.advanced-form {
  width: 700px;
  border-radius: 10px;
  padding: 20px;
  background: rgba(116, 109, 109, 0.5);
}
</style>