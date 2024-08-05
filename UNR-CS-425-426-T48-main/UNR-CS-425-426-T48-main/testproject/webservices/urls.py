from django.urls import path
from .views import *

urlpatterns = [
    path('Building/', buildingList.as_view()),
    path('buildings/<int:pk>/', buildingDetail.as_view(), name = 'building-detail'),
    path('libraries/', libraryList.as_view(), name='library-list'),
    path('libraries/building/<int:building>/', libraryDetail.as_view(), name='library-detail-by-building'),
    path('hangoutspots/', hangoutspotsList.as_view(), name='hangoutspots-list'),
    path('hangoutspots/building/<int:building>/', hangoutspotsDetail.as_view(), name='hangoutspots-detail-by-building'),
    path('restrooms/', restroomList.as_view(), name='restroom-list'),
    path('restrooms/building/<int:building>/', restroomDetail.as_view(), name='restroom-detail-by-building'),
    path('vendingmachines/', vendingmachineList.as_view(), name='vendingmachine-list'),
    path('vendingmachines/building/<int:building>/', vendingmachineDetail.as_view(), name='vendingmachine-detail-by-building'),
    path('resources/', resourceList.as_view(), name='resource-list'),
    path('resources/building/<int:building>/', resourceDetail.as_view(), name='resource-detail-by-building'),
    path('store/', storeList.as_view(), name='store-list'),
    path('store/building/<int:building>/', storeDetail.as_view(), name='store-detail-by-building'),
    path('dining_option/', dining_optionList.as_view(), name='dining_option-list'),
    path('dining_option/building/<int:building>/', dining_optionDetail.as_view(), name='dining_option-detail-by-building'),
    path('labs/', labList.as_view(), name='lab-list'),
    path('labs/building/<int:building>/', labDetail.as_view(), name='lab-detail-by-building'),
    path('elevators/', elevatorList.as_view(), name='elevator-list'),
    path('elevators/building/<int:building>/', elevatorDetail.as_view(), name='elevator-detail-by-building'),
    path('staircases/', staircaseList.as_view(), name='staircase-list'),
    path('staircases/building/<int:building>/', staircaseDetail.as_view(), name='staircase-detail-by-building'),
    path('special_features/', special_featureList.as_view(), name='special_features-list'),
    path('special_features/building/<int:building>/', special_featureDetail.as_view(), name='special_features-detail-by-building'),
    ]
