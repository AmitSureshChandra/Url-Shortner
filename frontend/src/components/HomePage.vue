<template>
  <url-page />
</template>

<script>

import UrlPage from './UrlPage.vue'

export default {
  name: "HomePage",
  components : {
      UrlPage
  },
  data: () => {
    return {
      search: "",
      loading: false,
      currentTime: null,
      loggedIn: false,
      data: [],
    };
  },
  methods: {
  },
};
</script>

<style>
</style>