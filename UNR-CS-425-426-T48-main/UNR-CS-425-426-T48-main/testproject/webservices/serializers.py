from rest_framework import serializers
from .models import building, library, hangoutspots, restroom, vendingmachine, resource, store, dining_option, lab, elevator, staircase, special_feature

class buildingSerializer(serializers.ModelSerializer):
    class Meta:
        model = building
        fields = '__all__'

class librarySerializer(serializers.ModelSerializer):  
    class Meta:
        model = library
        fields = '__all__'

class hangoutspotsSerializer(serializers.ModelSerializer):
    class Meta:
        model = hangoutspots
        fields = '__all__'

class restroomSerializer(serializers.ModelSerializer):
    class Meta:
        model = restroom
        fields = '__all__'

class vendingmachineSerializer(serializers.ModelSerializer):
    class Meta:
        model = vendingmachine
        fields = '__all__'

class resourceSerializer(serializers.ModelSerializer):
    class Meta:
        model = resource
        fields = '__all__'

class storeSerializer(serializers.ModelSerializer):
    class Meta:
        model = store
        fields = '__all__'

class dining_optionSerializer(serializers.ModelSerializer):
    class Meta:
        model = dining_option
        fields = '__all__'

class labSerializer(serializers.ModelSerializer):
    class Meta:
        model = lab
        fields = '__all__'

class elevatorSerializer(serializers.ModelSerializer):
    class Meta:
        model = elevator
        fields = '__all__'

class staircaseSerializer(serializers.ModelSerializer):
    class Meta:
        model = staircase
        fields = '__all__'

class special_featureSerializer(serializers.ModelSerializer):
    class Meta:
        model = special_feature
        fields = '__all__'
