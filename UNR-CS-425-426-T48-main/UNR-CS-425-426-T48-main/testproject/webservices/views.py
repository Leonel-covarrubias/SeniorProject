from django.shortcuts import render
from .models import *
from rest_framework import generics
from .serializers import *
from django.shortcuts import get_object_or_404
from rest_framework.response import Response

# Create your views here.
class buildingList(generics.ListAPIView): #Returns a list of all buildings
    queryset = building.objects.all()
    serializer_class = buildingSerializer
    print(queryset)

class buildingDetail(generics.RetrieveUpdateDestroyAPIView): #Returns a single Building Object by primary key
    queryset = building.objects.all()
    serializer_class = buildingSerializer

class libraryList(generics.ListAPIView): 
    queryset = library.objects.all()
    serializer_class = librarySerializer

class libraryDetail(generics.ListAPIView):
    serializer_class = librarySerializer

    def get_queryset(self):
        """
        This view should return a list of all the elevators
        for the building as determined by the building portion of the URL.
        """
        building = self.kwargs['building']
        return library.objects.filter(building=building)


class hangoutspotsList(generics.ListAPIView): 
    queryset = hangoutspots.objects.all()
    serializer_class = hangoutspotsSerializer

class hangoutspotsDetail (generics.ListAPIView):
    serializer_class = hangoutspotsSerializer

    def get_queryset(self):
        """
        This view should return a list of all the elevators
        for the building as determined by the building portion of the URL.
        """
        building = self.kwargs['building']
        return hangoutspots.objects.filter(building=building)


class restroomList(generics.ListAPIView):
    queryset = restroom.objects.all()
    serializer_class = restroomSerializer

class restroomDetail(generics.ListAPIView):
    serializer_class = restroomSerializer

    def get_queryset(self):
        """
        This view should return a list of all the elevators
        for the building as determined by the building portion of the URL.
        """
        building = self.kwargs['building']
        return restroom.objects.filter(building=building)

class vendingmachineList(generics.ListAPIView): 
    queryset = vendingmachine.objects.all()
    serializer_class = vendingmachineSerializer

class vendingmachineDetail(generics.ListAPIView):
    serializer_class = vendingmachineSerializer

    def get_queryset(self):
        """
        This view should return a list of all the elevators
        for the building as determined by the building portion of the URL.
        """
        building = self.kwargs['building']
        return vendingmachine.objects.filter(building=building)

class resourceList(generics.ListAPIView): 
    queryset = resource.objects.all()
    serializer_class = resourceSerializer

class resourceDetail(generics.ListAPIView):
    serializer_class = resourceSerializer

    def get_queryset(self):
        """
        This view should return a list of all the elevators
        for the building as determined by the building portion of the URL.
        """
        building = self.kwargs['building']
        return resource.objects.filter(building=building)

class storeList(generics.ListAPIView): 
    queryset = store.objects.all()
    serializer_class = storeSerializer

class storeDetail(generics.ListAPIView):
    serializer_class = storeSerializer

    def get_queryset(self):
        """
        This view should return a list of all the elevators
        for the building as determined by the building portion of the URL.
        """
        building = self.kwargs['building']
        return store.objects.filter(building=building)

class dining_optionList(generics.ListAPIView): 
    queryset = dining_option.objects.all()
    serializer_class = dining_optionSerializer

class dining_optionDetail(generics.ListAPIView):
    serializer_class = dining_optionSerializer

    def get_queryset(self):
        """
        This view should return a list of all the dining options
        for the building as determined by the building portion of the URL.
        """
        building = self.kwargs['building']
        return dining_option.objects.filter(building=building)


class labList(generics.ListAPIView):
    queryset = lab.objects.all()
    serializer_class = labSerializer

class labDetail(generics.ListAPIView):
    serializer_class = labSerializer

    def get_queryset(self):
        """
        This view should return a list of all the elevators
        for the building as determined by the building portion of the URL.
        """
        building = self.kwargs['building']
        return lab.objects.filter(building=building)

class elevatorList(generics.ListAPIView):
    queryset = elevator.objects.all()
    serializer_class = elevatorSerializer

class elevatorDetail(generics.ListAPIView):
    serializer_class = elevatorSerializer

    def get_queryset(self):
        """
        This view should return a list of all the elevators
        for the building as determined by the building portion of the URL.
        """
        building = self.kwargs['building']
        return elevator.objects.filter(building=building)

class staircaseList(generics.ListAPIView):
    queryset = staircase.objects.all()
    serializer_class = staircaseSerializer

class staircaseDetail(generics.ListAPIView):
    serializer_class = staircaseSerializer

    def get_queryset(self):
        """
        This view should return a list of all the elevators
        for the building as determined by the building portion of the URL.
        """
        building = self.kwargs['building']
        return staircase.objects.filter(building=building)

class special_featureList(generics.ListAPIView):
    queryset = special_feature.objects.all()
    serializer_class = special_featureSerializer

class special_featureDetail(generics.ListAPIView):
    serializer_class = special_featureSerializer

    def get_queryset(self):
        """
        This view should return a list of all the elevators
        for the building as determined by the building portion of the URL.
        """
        building = self.kwargs['building']
        return special_feature.objects.filter(building=building)


