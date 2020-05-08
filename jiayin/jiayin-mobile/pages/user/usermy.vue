<template>
	<view class="uni-container">
		<view class="user">
			<view>
				 <image class="userhead"  src="../../static/img/pig.jpg" mode="aspectFit"></image>
			</view>
	    <view>
		<view>
				<text  >陈哲</text>
		</view>
	    <view>
			   <text >账号管理</text>
	    </view>
	</view>
		
		</view>
		<view class="my">
			<view class="my_item">
				<view class="iconfont icon-shoucang"></view>
				<view  class="my_font">收藏</view>
			</view>
			<view class="my_item">
				<view class="iconfont icon-zuji"></view>
				<view class="my_font">足迹</view>
			</view>
			<view class="my_item">
				<view class="iconfont .icon-draft"></view>
				<view class="my_font">草稿箱</view>
			</view>
			<view class="my_item">
				<view class="iconfont .icon-lianxiwomen1-copy"></view>
				<view class="my_font">联系我们</view>
			</view>
	
		</view>
	</view>

</template>

<script>
	export default {
		data() {
			return {
				
			}
		},
		methods: {
			
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
    .my{
	    width: 90%;
	    height: 250rpx;
	    display: flex;
	    background-color: #FFFFFF;
	    margin: 10rpx auto;
	  
	}   
	.my_item{
		width: 25%;
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
   