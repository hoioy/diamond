<template>
	<view class="uni-container">
		

		<view class="nav">
			<view class="nav_item" v-for="(item,index) in navs" :key="index" @click="pageJump(item.path,item.msgTypeParentId)">
				<view :class="item.icon"></view>
				<view  class="my_font">{{item.title}}</view>
			</view>
		</view>
	</view>

</template>

<script>
	export default {
		data() {
			return {
				userinfo:{
					nickName:"",
					avatarUrl:""
					
				},
				alreadyLogin: uni.getStorageSync('token'),
				navs:[
					{
						icon:'iconfont icon-tudi',
						title: '土地交易',
						path: '/pages/message/land-sale/land-sale' ,
						msgTypeParentId:'2c53d081bbcd42592c5ca04869bb1271'
					},
					{
						icon:'iconfont icon-nongji',
						title: '用车',
						path: '/pages/publish-history/history-list/publish-history-list' 
					},
					{
						icon:'iconfont tudi1',
						title: '相关',
						path: '/pages/contact/contact-our' 
					},
					{
						icon:'iconfont liangshi',
						title: '粮食',
						path: '/pages/contact/contact-our' 
					},
					{
						icon:'iconfont fangwu',
						title: '房屋',
						path: '/pages/contact/contact-our' 
					},
					{
						icon:'iconfont shunfengche',
						title: '顺风车',
						path: '/pages/contact/contact-our' 
					},
					{
						icon:'iconfont zhaogongren',
						title: '用工',
						path: '/pages/contact/contact-our' 
					},
					{
						icon:'iconfont zhusu',
						title: '住宿',
						path: '/pages/contact/contact-our' 
					}
				]
				
			}
		},
		methods: {
	pageJump(url,msgTypeParentId){
			 uni.navigateTo({
			 url:	url+'?msgTypeParentId='+msgTypeParentId
			 })	
			}
			
		}
	}
</script>

<style>
	.userhead{
		left: auto;
		height: 100rpx;
		width: 100rpx;
	}
	
    .user{
		width: 750rpx;
		height: 200rpx;
		background-color: #FFD700;
		z-index: -1;
		display: flex;
	}
    .nav{
	    width: 90%;
	    height: 250rpx;
	    display: flex;
	    background-color: #FFFFFF;
	    margin: 10rpx auto;
	  
	}   
	.nav_item{
		width: 20%;
		text-align: center;
		
	}
	.iconfont{
		width: 100rpx;
		height: 100rpx;
		background: #FFFFFF;
		margin: 10rpx auto;
		line-height: 120rpx;
		color: #FFD700;
		font-size: 50rpx;
	}
	.my_font{
		font-size: 30rpx;
	}
</style>
   