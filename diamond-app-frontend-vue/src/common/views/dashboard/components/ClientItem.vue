<template>
  <div class="client-item">
    <a :href="baseUrl" target="_blank" style="text-align: center">
      <img :src="image" alt="" class="img hover-transition">
      <h4 class="customName cut-text">
        <a href="">{{ customName }}</a>
      </h4>
      <p class="customSubName cut-text">
        {{ customSubName }}
      </p>
    </a>
  </div>
</template>

<script>
export default {
  name: 'ClientItem',
  props: {
    baseUrl: {
      type: String,
      required: true
    },
    image: {
      type: String,
      required: true
    },
    customName: {
      type: String,
      required: true
    },
    customSubName: {
      type: String,
      required: true
    }

  }
}
</script>

<style lang="scss" scoped>
    .client-item{
        float: left;
        width: 22%;
        height: 250px;
        padding: 10px 15px;
        margin:10px 10px;
        border: 1px solid #d3d3d3;
        .img {
            width: 50%;
            height: 100px;
          margin-left: 25%;
        }
        .cut-text {
            display: -webkit-box;
            -webkit-box-orient: vertical;
            -webkit-line-clamp: 2;
            overflow: hidden;
        }
        .customName {
            /*margin: 10px 41px;*/
          padding-left:10%;
          text-align: center;
        }
        .customSubName {
            line-height: 20px;
            height: 40px;
        }
    }

</style>
