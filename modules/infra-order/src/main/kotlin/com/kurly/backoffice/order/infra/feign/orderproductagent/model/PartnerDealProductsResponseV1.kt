package com.kurly.backoffice.order.infra.feign.orderproductagent.model

import java.time.LocalDateTime

data class PartnerDealProductsResponseV1(
    val totalPartners: Int,
    val totalDeals: Int,
    val partnerDealProducts: List<PartnerDealProductDto>,
    val tam: TamDto?,
) {
    data class PartnerDealProductDto(
        val partyType: String,
        val partnerCode: String,
        val dealProducts: List<DealProductDto>,
    )

    data class TamDto(
        val deliveryOperationTime: String,
        val providerName: String,
        val regionGroupCode: String,
        val clusterCenterCode: String,
        val unavailableTime: UnavailableTimeDto?,
    )

    data class UnavailableTimeDto(
        val startAt: String,
        val endAt: String,
        val noticeMessage: String,
    )

    data class DealProductDto(
        val masterProductName: String,
        val masterProductCode: String,
        val masterProductNo: Long,
        val exceptionLabel: String?,
        val dealProductName: String,
        val dealProductCode: String,
        val dealProductNo: Long,
        val contentsProductName: String,
        val contentsProductCode: String,
        val contentsProductNo: Long,
        val productPrice: Long,
        val discountPrice: Long,
        val sellingPrice: Long,
        val retailPrice: Long,
        val quantity: Int,
        val categoryCodes: List<String>,
        val status: String,
        val imageUrl: String?,
        val pointPolicy: String?,
        /** 일반 주문유형정책 */
        val normalOrderTypePolicyInContents: String?,
        val operatingRule: String?,
        val isUseStock: Boolean,
        val isSale: Boolean,
        val isSoleOut: Boolean,
        val storageType: String?,
        val taxType: String,
        val supportDelivery: List<String>,
        val purchaseQuantityPolicy: PurchaseQuantityPolicyDto,
        val soldOutAltText: String?,
        val soldOutAltType: String?,
        val stockQuantity: Int,
        val tagNames: List<String>?,
        val legacyPromotion: String?,
        val reservationPossibleDate: String?,
        val isDeliveryProduct: Boolean,
        val isAdult: Boolean,
        var fulfillmentInfo: FulfillmentInfoDto,
        val legacyCoupon: LegacyCouponDto?,
    )

    data class PurchaseQuantityPolicyDto(
        val buyUnit: Int,
        val min: Int,
        val max: Int?,
    )

    data class FulfillmentInfoDto(
        val fulfillmentId: Int,
        val fulfillmentOwner: String,
        val thirdPartyFulfillmentId: String,
        val partnerPortalId: String?,
    )

    data class LegacyCouponDto(
        val type: String,
        val isExpired: Boolean,
        val expiredAt: LocalDateTime,
    )

}
