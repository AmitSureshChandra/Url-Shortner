<template>
  <div>
    <v-card>
      <v-card-title>
        Short Urls
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
        </template>

        <template slot="item.shortUrl" slot-scope="{ item }">
          <a :href="getHost() + item.shortUrl" target="_blank"><span> {{ getHost() + item.shortUrl }}</span></a>
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
                        :error-messages="errors.shortUrl"
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
                        :error-messages="errors.url"
                    ></v-text-field>
                  </v-col>
                </v-row>
              </v-form>
            </v-container>
            <small>*indicates required field</small>
            <br>
<!--            <strong style="color: red">{{ error }}</strong>-->
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="error" small dark @click="cancelDialog">
              Cancel
            </v-btn>
            <v-btn color="primary" small @click="saveUrl"
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
      errors: {},
      shortUrl: "",
      fullUrl: "",
      loading: false,
      dialog: false,
      headers: [
        {
          text: "Short URL",
          align: "start",
          value: "shortUrl",
        },
        { text: "Full Url", value: "fullUrl" },
        { text: "Visit Count", value: "cnt" },
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
            if(e.response.data.msg)
              this.$swal(
                  `${e.response.data.msg}! `,
                  "",
                  "error"
              )

            else
              this.errors = e.response.data
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
      console.log(item.shortUrl + " copied to clipboard")
      navigator.clipboard.writeText(process.env.VUE_APP_API_URL + "/" + item.shortUrl)
    },
    cancelDialog(){
      this.dialog = false
      this.shortUrl = ""
      this.fullUrl = ""
    },
    getHost(){
      return process.env.VUE_APP_API_URL+ "/"
    }
  }
};

</script>

<style scoped>

</style>