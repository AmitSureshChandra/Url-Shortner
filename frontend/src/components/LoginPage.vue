<template>
  <div id="app">
    <v-app>
      <v-main>
        <v-layout align-center>
          <v-container fluid class="login-container">
            <v-row justify="center" align="center">
              <v-col cols="12" sm="8" md="6" lg="4">
                <v-card class="login-card">
                  <v-tabs v-model="tab">
                    <v-tab>LOGIN</v-tab>
                    <v-tab>REGISTER</v-tab>
                  </v-tabs>
                  <v-card-text>
                    <v-form ref="login_form" v-if="tab === 0" @submit.prevent="login">
                      <v-text-field required :rules="rules" v-model="email" label="Email" type="email" class="input-field"></v-text-field>
                      <v-text-field required :rules="rules"  v-model="password" label="Password"  class="input-field" :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'" :type="show1 ? 'text' : 'password'"  @click:append="show1 = !show1"></v-text-field>
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
                    <v-form ref="register_form" v-else @submit.prevent="register">
                      <v-text-field  required :rules="rules"  v-model="newUsername" label="Username" class="input-field"></v-text-field>
                      <v-text-field  required :rules="rules"  v-model="newEmail" label="Email" type="email" class="input-field"></v-text-field>
<!--                      <v-text-field  required :rules="rules"  v-model="newMobile" label="Mobile" type="text" class="input-field" pattern="/^\d{10}$/"></v-text-field>-->
                      <v-text-field  required :rules="rules"  v-model="newPassword" label="Password" class="input-field" :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'" :type="show1 ? 'text' : 'password'" @click:append="show1 = !show1"></v-text-field>
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
      rules: [
        value => {
          if (value) return true

          return 'required'
        },
      ],
    };
  },

  methods: {
    login() {
      if(!this.$refs.login_form.validate()) return

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
      if(!this.$refs.register_form.validate()) return
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
