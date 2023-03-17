<template>
  <div id="app">
    <v-app>
      <v-main>
        <login-page v-if="!auth" @loggedIn="setAuth()" />
        <div v-else>
          <v-app-bar>
<!--            <v-app-bar-nav-icon-->
<!--              @click.stop="drawer = !drawer"-->
<!--            ></v-app-bar-nav-icon>-->
            <v-app-bar-title style="font-size: 20px">
              Url Shortner </v-app-bar-title
            >
            <v-spacer />
<!--            <v-btn-->
<!--              :disabled="loading"-->
<!--              right-->
<!--              text-->
<!--              class="primary float-right"-->
<!--              @click="logout()"-->
<!--            >-->
<!--              Logout-->
<!--              <v-progress-circular-->
<!--                class="ml-1"-->
<!--                v-if="loading === true"-->
<!--                indeterminate-->
<!--                :width="3"-->
<!--                size="20"-->
<!--                color="white"-->
<!--              ></v-progress-circular>-->
<!--            </v-btn>-->

            <v-menu offset-y class="ml-2">
              <template #activator="{ on }">
                <v-btn icon v-on="on">
                  <v-avatar size="32">
                    <img :src="profileImage" alt="Profile Image">
                  </v-avatar>
                </v-btn>
              </template>
              <v-list>
                <v-list-item>
                  <v-list-item-avatar>
                    <img :src="profileImage" alt="Profile Image">
                  </v-list-item-avatar>
                  <v-list-item-title>{{ user.name }}</v-list-item-title>
                  <v-list-item-subtitle>{{ user.email }}</v-list-item-subtitle>
                </v-list-item>
                <v-divider></v-divider>
                <v-list-item v-for="item in menuItems" :key="item.title"  @click="logout()">
                  <v-list-item-icon>
                    <v-icon>{{ item.icon }}</v-icon>
                  </v-list-item-icon>
                  <v-list-item-title>{{ item.title }}</v-list-item-title>
                </v-list-item>
              </v-list>
            </v-menu>
          </v-app-bar>
          <v-navigation-drawer v-model="drawer" absolute temporary>
            <v-list-item>
              <v-list-item-avatar>
                <v-img
                  src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOAAAADgCAMAAAAt85rTAAAAaVBMVEX///9mZmbv7+9sbGxiYmJdXV1cXFxgYGBYWFjd3d1WVlZ0dHT19fXx8fFnZ2fl5eXGxsanp6ebm5vV1dWLi4vPz8/5+fl+fn6tra2Tk5O2traCgoLg4OCwsLDp6el3d3e/v7+enp5NTU32r3OnAAAG4klEQVR4nO2d2ZaiMBCGG83C7gIoS6vY7/+QA9K2Om4kVFKJJ9/VXPXxnxS1pSi+vhwOh8PhcDgcDofD4XA4HA6HoWz238u6XJ2o22wfY/8gSIJ2vU39H5/RAeaHIYnyuvgElfs29yjjxPsfQijlh11gtchNm6eM32m7UsnItpxh/0xZvlfcvz+5e41+vsT+qTJkDaNv1Z01eq1tlhrk9JVp3kuMlhvs3yzA7BiKyDtJDLcZ9s8eTc2ZoLxBYmWHu1k0MvJ66NwGb9OmotZ5dYiswv75b1mH0vJ6WLTAVvCS/VbWPP8O0TPZTBeHsaHvhULfXIWLEYnLCMIdtpAnZNOPb4AdsaU8JPNAzq/HL7HFPGD/oCSSV1hjy7ljRgD1dc+haZ4m3sqH94fwAlvSLeup8e9/CDGqgKp9YH3dETbYoq5YTMvPHsNW2LL+2GxBHcwZEmALO1PCG+hJ4Bxb2C+BkvPrYIbE+wY4QlwgRtROmQoPM0DX2OI6NgdVFtrBDPAzrRoPM8BzbHndE6jwADsj/cbWV6g8wO4I0btQ6lzogI/cK11AVfHPYMj9i51qgeSAW1Uoltfho0aKTK2L6aGoHagSus69h0SI92qK6qRbOKKN7hXHiBMUscNWqMuzLxDE3kWlOkicCPEE6ngEvfRnj6VvNtegr4uEaE1gZb2KWyha52Kp5RFErChqPQJJgxXqV3oEehFWvl3piPMdc6yaMNfjZDwPK05EugRitUd1CaRY6bYugWjdUScQCI7VHFXZtL8G7Q7m48OEtkCfIAksPz1V+/hku9BjohztGvT70wve5NNbFrqaTlhO9Otr/eltQ4UDFhcwL3n3GvShtu43aicQBlBH1nYars8OiPrUzFHegjywpr4kZLiTMrVqGyVb3BcnZ6pP0G9R9amvCRn2bHqm1kZxZyxO5EqPkKNdfv6hdBrPiIlYla0ngj5N2fGtLtgb8uqEulkLtHbaLYtUkT70GHimVmOkHDXNvkFNtE/xQ8SZREW0N8ZAe5bwwdCwpQjg72cZ82bWL5sK1kpJasRrS1fEsKUvM+wV3o7EA3SloXn6vr72EZhCQ9dZLFIgK8XshL4EyEqNWxJwIZHednSBUJOXV8XrqfGQRga8E/mK3bSdMmFuRoX0giCSN1POTHUv18RHJnmI/tZw8zxTzGUOkfs7e7b/1UxUIgkrc8q/ESSrVEQi57kJ/TMh9iuPjnwWOW1Mjn1PSeoofN9wI6F3tMS3PCBYReyFTyWUeZXlq4zjoGxS+mDRL+n80GG9tGPj5htmWVul/fJpfyAMfUqasljYExbGMAuy5UARfJg068k+4kF6xqzmP6GKMJY0PyV+a21/9PqMhZXgD1Xm8S5TyHFD5GxFfhMyv4H9z96UQ/jkrELM4UpyyTc5bQH/cnD4+8uUrZEygey/ytYH2wSeHG/aApRglMHx+i6VJuwI4k7bu54AA34ARvC4McHm09fbZM2Drg4nmluJ5c+TDJrRad8dyA7h478c5hqTnzh/UczSeS1rqHHRPN+jyyNtFf+7beH+fCXj2uP28LLlyFNNNzIBedugp+G2FTvGTVaxtx1VPauNi1FNQcJI3o61qSRbRy8/DfOnUMP073L0/QpnXlNn71zOZtFWkT+2e6N+9ElsfUVftefl8lkQS4q6mnNf5ErKVzydJzFNQbpSnkX5cddmWfBLtqxX1ZaEo0/uSqHSAdJCdirt9BUpzskv3T+p7GJ1lVP4C13v7L5E3QXNTPZSBRhVN/jAgyITYGpSb/Bt79KQSMUt6U79GsPRUAWr1gpjzq8HPuAnUDMwQID38fTswBMAeJxbwecWJkJBF1QrGzufAIOMho1pBtpD4bqJSpehSwO3RT3BlvIEME+6MioEXoDaUB1oWosjDlDlpGtzkwQUopNuVo52C8imGWOKpEeE0wunVsc+Dmmmhwotq8InMHnRxdLgJ7CHTn3DyegnsGfi5krTD3DyU2hwDDzjT7lVC4x2oQN0SvdC1/LCSRD5dGZmYhl4x4RXYTUsNAJAfuNMbIGL6ZF+2Qnsy8iKkQ72xrUKn8HkbHRjxRPYI9lgMz+LOUPkshkrguCA1OIneyy0q3tbCYFLC9K0M1I2urbHQj1vLp5xJ5ZE+QEqPsam6Ys8QEhs71K+tBAW8d0lOlajAsKF+4d26RNPZmxJtM8I97hbywQKL8q1Kgr2cMGhBKuiYE8o1h+NbdMn2pnRsH0ZGC42KKvl456gCObbluUxPWIX9kfbnKjnpUL938o6J+NRoWRN2xeV4BC6RzP9XvcRQiWhLT3ta4SWBcbbuXV4QkX9zELc9giHw+FwOBwOh8PhcDgcDocm/gFFC4MQwyc3MwAAAABJRU5ErkJggg=="
                ></v-img>
              </v-list-item-avatar>

              <v-list-item-content>
                <v-list-item-title>{{
                  this.user && this.user.name ? this.user.name : "User"
                }}</v-list-item-title>
              </v-list-item-content>
            </v-list-item>

            <v-divider></v-divider>

            <v-list dense>
              <v-list-item
                selectable
                v-for="(item, index) in items"
                :key="item.title"
                link
                @click="
                  {
                    value = index;
                    drawer = false;
                  }
                "
                :class="index === value ? 'primary white--text' : ''"
              >
                <v-list-item-icon>
                  <v-icon :color="index === value ? 'white' : ''">{{
                    item.icon
                  }}</v-icon>
                </v-list-item-icon>

                <v-list-item-content>
                  <v-list-item-title>{{ item.title }}</v-list-item-title>
                </v-list-item-content>
              </v-list-item>
            </v-list>
          </v-navigation-drawer>
          <v-container>
            <template v-if="value === 0">
              <home-page />
            </template>
            <template v-if="value === 1">
              <url-page />
            </template>
            <template v-else> </template>
          </v-container>
        </div>
      </v-main>
    </v-app>
  </div>
</template>

<script>
import HomePage from "./components/HomePage.vue";
import LoginPage from "./components/LoginPage.vue";
import UrlPage from "./components/UrlPage.vue"

import axios from "axios";
export default {
  name: "App",
  components: { HomePage, LoginPage, UrlPage },
  data() {
    return {
      auth: false,
      email: "",
      password: "",
      loading: false,
      user: {
        email: "",
        name: "",
      },
      value: 0,
      drawer: false,
      items: [
        { title: "Home", icon: "mdi-view-dashboard" },
        { title: "Analytics", icon: "mdi-wallet-membership" },
      ],
      menuItems: [
        { title: "Logout", route: "/logout", icon: "mdi-logout" },
      ],
      profileImage : "data:image/png;base64,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"
    };
  },
  mounted() {
    let isLoggedIn = JSON.parse(localStorage.getItem("loggedIn"));
    if (isLoggedIn && isLoggedIn === true) {
      axios.defaults.headers.Authorization = JSON.parse(
        localStorage.getItem("access_token")
      );
      this.user = JSON.parse(localStorage.getItem("user"));
      this.auth = true;
    }
  },
  methods: {
    setAuth() {
      this.auth = true;
      this.user = JSON.parse(localStorage.getItem("user"));
    },
    logout() {
      localStorage.clear();
      this.auth = false;

      // this.loading = true;
      // axios
      //   .post("/api/logout", {
      //     email: this.email,
      //     password: this.password,
      //   })
      //   .then(({ status }) => {
      //     if (status == 200) {
      //       localStorage.clear();
      //       this.auth = false;
      //     }
      //   })
      //   .catch((e) => {
      //     console.log({ e });
      //   })
      //   .finally(() => {
      //     this.loading = false;
      //   });
    },
  },
};
</script>

<style>
.v-app-bar-title__content {
  width: 125px;
}
</style>
