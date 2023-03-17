<template>
  <div>
    <v-card>
      <v-card-title>
        Short Urls
<!--        <v-btn-->
<!--           -->
<!--            color="blue-grey"-->
<!--            class="ml-2 white&#45;&#45;text"-->
<!--            small-->
<!--            -->
<!--            prepend-icon="mdi-cloud-upload"-->
<!--        >-->
<!--          Create New-->
<!--        </v-btn>-->
        <v-btn class="ml-4"  small color="primary" @click="dialog = true" :loading="loading"
               :disabled="loading">
          <v-icon left>mdi-plus</v-icon>
          Create New
        </v-btn>
        <v-spacer></v-spacer>
        <v-text-field
            v-model="search"
            append-icon="mdi-magnify"
            label="Search"
            single-line
            hide-details
        ></v-text-field>
      </v-card-title>
      <v-data-table
          :headers="headers"
          :items="data"
          :loading="loading"
          :search="search"
      >
        <template slot="item.actions" slot-scope="{ item }">
          <v-btn color="primary" fab small>
            <v-icon small @click="copy(item)">mdi-content-copy</v-icon>
          </v-btn>
<!--          <v-btn color="secondary" fab small>-->
<!--            <v-icon small @click="deleteItem(item)">mdi-delete</v-icon>-->
<!--          </v-btn>-->

        </template>
      </v-data-table>
    </v-card>
    <v-row justify="center">
      <v-dialog
          v-model="dialog"
          persistent
          width="1024"
      >
        <v-card>
          <v-card-title>
            <span class="text-h5">Add Short Url</span>
          </v-card-title>
          <v-card-text>
            <v-container>
              <v-form ref="form">
                <v-row>
                  <v-col
                      cols="12"
                      sm="6"
                      md="4"
                  >
                    <v-text-field
                        label="Short Url*"
                        hint="my-profile"
                        required
                        :rules="rules"
                        v-model="shortUrl"
                    ></v-text-field>
                  </v-col>
                  <v-col
                      cols="12"
                      sm="6"
                      md="4"
                  >
                    <v-text-field
                        label="Full Url*"
                        :rules="rules"
                        hint="https://github.com/AmitSureshChandra"
                        v-model="fullUrl"
                    ></v-text-field>
                  </v-col>
                </v-row>
              </v-form>
            </v-container>
            <small>*indicates required field</small>
            <br>
            <strong style="color: red">{{ error }}</strong>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
<!--            <v-btn-->
<!--                variant="flat"-->
<!--                color="error"-->
<!--                -->
<!--            >-->
<!--              Close-->
<!--            </v-btn>-->
            <v-btn color="error" dark @click="dialog = false">
              <v-icon left>mdi-delete</v-icon>
              Cancel
            </v-btn>
<!--            <v-btn-->
<!--                variant="flat"-->
<!--                color="secondary"-->
<!--               -->
<!--            >-->
<!--              Save-->
<!--            </v-btn>-->
            <v-btn color="primary"  @click="saveUrl"
                   :loading="loading">
              <v-icon left>mdi-content-save</v-icon>
              Save Changes
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-row>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: "UrlPage",
  mounted() {
    this.loadRoutes()
  },
  data: () => {
    return {
      search: "",
      error: "",
      shortUrl: "",
      fullUrl: "",
      loading: false,
      dialog: false,
      headers: [
        {
          text: "shortUrl",
          align: "start",
          value: "shortUrl",
        },
        { text: "fullUrl", value: "fullUrl" },
        { text: "CreatedAt", value: "created_at" },
        { text: 'Actions', value: 'actions', sortable: false },
      ],
      data: [],
      rules: [
        value => {
          if (value) return true

          return 'required'
        },
      ],
    };
  },
  methods: {
    saveUrl(){

      if(!this.$refs.form.validate()) return

      this.loading = true

      axios.post("/api/v1/routes", {
        url: this.fullUrl,
        shortUrl: this.shortUrl
      })
          .then(({data}) => {
            console.log({data})
            this.dialog = false
            this.loadRoutes()
          })
          .catch(e => {
            console.log({e})
            this.error = e.response.data.msg
          })
          .finally(() => {
            this.loading = false
          })

    },
    loadRoutes(){
      this.loading = true;
      axios
          .get("/api/v1/routes/users")
          .then(({ data }) => {
            console.log({data})
            this.data = data;
          })
          .catch((e) => {
            console.log({ e });
          })
          .finally(() => {
            this.loading = false;
          });
    },
    copy(item){
      console.log(item)
    }
  }
};

</script>

<style scoped>

</style>