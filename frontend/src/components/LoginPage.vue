<template>
  <div id="app">
    <v-app>
      <v-main>
        <v-layout align-center>
<!--          <v-container style="width: 250px">-->
<!--            <v-row justify="center">-->
<!--              <v-card width="400" height="250">-->
<!--                <v-card-title class="text-center"> Url Shortner</v-card-title>-->

<!--                <v-card-text>-->
<!--                  <v-row>-->
<!--                    <v-col class="my-0" cols="12">-->
<!--                      <v-text-field-->
<!--                        type="email"-->
<!--                        label="Email"-->
<!--                        dense-->
<!--                        v-model="email"-->
<!--                      />-->
<!--                    </v-col>-->

<!--                    <v-col class="my-0" cols="12">-->
<!--                      <v-text-field-->
<!--                        v-model="password"-->
<!--                        :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"-->
<!--                        :rules="[rules.required]"-->
<!--                        :type="show1 ? 'text' : 'password'"-->
<!--                        name="input-10-1"-->
<!--                        label="Password"-->
<!--                        counter-->
<!--                        dense-->
<!--                        @click:append="show1 = !show1"-->
<!--                      ></v-text-field>-->
<!--                    </v-col>-->
<!--                  </v-row>-->
<!--                </v-card-text>-->
<!--                <v-card-actions class="float-right">-->
<!--                  <v-btn-->
<!--                    @click="login()"-->
<!--                    :disabled="loading"-->
<!--                    depressed-->
<!--                    color="primary"-->
<!--                  >-->
<!--                    Login-->

<!--                    <v-progress-circular-->
<!--                      class="ml-1"-->
<!--                      v-if="loading == true"-->
<!--                      indeterminate-->
<!--                      :width="3"-->
<!--                      size="20"-->
<!--                      color="white"-->
<!--                    ></v-progress-circular>-->
<!--                  </v-btn>-->
<!--                </v-card-actions>-->
<!--              </v-card>-->
<!--            </v-row>-->
<!--          </v-container>-->
          <v-container fluid class="login-container">
            <v-row justify="center" align="center">
              <v-col cols="12" sm="8" md="6" lg="4">
                <v-card class="login-card">
<!--                  <v-card-title class="text-center">Url Shortner Login</v-card-title>-->
<!--                  <v-card-text>-->
<!--                    <v-form @submit.prevent="loginUser">-->
<!--                      <v-text-field v-model="email"  label="Email" type="email" class="input-field"></v-text-field>-->
<!--                      <v-text-field :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'" @click:append="show1 = !show1" v-model="password" label="Password" :rules="[rules.required]" :type="show1 ? 'text' : 'password'" class="input-field"></v-text-field>-->
<!--                      <v-btn type="submit" color="primary" block class="submit-btn">Login</v-btn>-->
<!--                    </v-form>-->
<!--                  </v-card-text>-->
                  <v-tabs v-model="tab">
                    <v-tab>LOGIN</v-tab>
                    <v-tab>REGISTER</v-tab>
                  </v-tabs>
                  <v-card-text>
                    <v-form v-if="tab === 0" @submit.prevent="login">
                      <v-text-field v-model="email" label="Email" type="email" class="input-field"></v-text-field>
                      <v-text-field v-model="password" label="Password"  class="input-field" :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'" :type="show1 ? 'text' : 'password'"  @click:append="show1 = !show1"></v-text-field>
                      <v-btn type="submit" color="primary" block class="submit-btn" :disabled="loading">
                        Login

                        <v-progress-circular
                            class="ml-1"
                            v-if="loading === true"
                            indeterminate
                            :width="3"
                            size="20"
                            color="white"
                        ></v-progress-circular>
                      </v-btn>
                    </v-form>
                    <v-form v-else @submit.prevent="register">
                      <v-text-field v-model="newUsername" label="Username" class="input-field"></v-text-field>
                      <v-text-field v-model="newEmail" label="Email" type="email" class="input-field"></v-text-field>
<!--                      <v-text-field v-model="newMobile" label="Mobile" type="text" class="input-field" pattern="/^\d{10}$/"></v-text-field>-->
                      <v-text-field v-model="newPassword" label="Password" class="input-field" :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'" :type="show1 ? 'text' : 'password'" @click:append="show1 = !show1"></v-text-field>
                      <v-btn type="submit" color="primary" block class="submit-btn" :disabled="loading">
                        Register
                        <v-progress-circular
                            class="ml-1"
                            v-if="loading === true"
                            indeterminate
                            :width="3"
                            size="20"
                            color="white"
                        ></v-progress-circular>
                      </v-btn>
                    </v-form>
                  </v-card-text>
                </v-card>
              </v-col>
            </v-row>
          </v-container>
        </v-layout>
      </v-main>
    </v-app>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "LoginPage",
  data() {
    return {
      tab: 0,
      newUsername: '',
      newEmail: '',
      newPassword: '',
      newMobile: '',
      show1: false,
      show2: true,
      email: "",
      loading: false,
      password: "",
      rules: {
        required: (value) => !!value || "Required.",
        min: (v) => v.length >= 8 || "Min 8 characters",
        emailMatch: () => `The email and password you entered don't match`,
      },
    };
  },

  methods: {
    login() {
      this.loading = true;
      axios.defaults.headers.Authorization = null
      axios
          .post("/api/v1/auth/login", {
            email: this.email,
            password: this.password,
          })
          .then(({ data, status }) => {
            if (status === 200) {
              localStorage.setItem("loggedIn", JSON.stringify(true));
              localStorage.setItem(
                  "access_token",
                  JSON.stringify("Bearer " + data['jwt-token'])
              );
              localStorage.setItem("user", JSON.stringify(data.user));

              // axios.defaults.headers.common["Authorization"] = "Bearer " + data['jwt-token']
              axios.defaults.headers.Authorization = "Bearer " + data['jwt-token']


              this.$swal(
                  `Welcome ${data.user.name}! `,
                  "Login Successful",
                  "success"
              ).then(() => {
                this.$emit("loggedIn");
              });
            }
          })
          .catch((e) => {
            console.log({ e });
            if(e.response.data.msg)
              this.$swal(
                  `${e.response.data.msg}! `,
                  "",
                  "error"
              )
          })
          .finally(() => {
            this.loading = false;
          });
    },
    register(){
      this.loading = true;
      axios.defaults.headers.Authorization = null
      axios
          .post("/api/v1/auth/register", {
            email: this.newEmail,
            password: this.newPassword,
            mobile: this.newMobile,
            username: this.newUsername,
          })
          .then(({ data, status }) => {
            if (status === 200) {
              localStorage.setItem("loggedIn", JSON.stringify(true));
              localStorage.setItem(
                  "access_token",
                  JSON.stringify("Bearer " + data['jwt-token'])
              );
              axios.defaults.headers.Authorization = "Bearer " + data['jwt-token']
              localStorage.setItem("user", JSON.stringify(data.user));
              // axios.defaults.headers.common["Authorization"] =

              this.$swal(
                  `Welcome ${data.user.name}! `,
                  "Register Successful",
                  "success"
              ).then(() => {
                this.$emit("loggedIn");
              });
            }
          })
          .catch((e) => {
            console.log({ e });
            if(e.response.data.msg)
              this.$swal(
                  `${e.response.data.msg}! `,
                  "",
                  "error"
              )
          })
          .finally(() => {
            this.loading = false;
          });
    }
  },
};
</script>

<style>
</style>
