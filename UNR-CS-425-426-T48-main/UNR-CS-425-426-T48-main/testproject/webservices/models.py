from django.db import models
from django.contrib.gis.db import models

# Create your models here.
class building(models.Model):
    gps_coords = models.PointField(blank = True, null = True, srid=4326)
    building_code = models.CharField(max_length=10, unique=True)
    building_name = models.CharField(max_length=255)
    building_num = models.CharField(max_length=10)
    department = models.CharField(max_length=255)
    operating_hours = models.CharField(max_length=255)

class library(models.Model):
    building = models.ForeignKey(building, on_delete=models.CASCADE)
    location = models.CharField(max_length=255)
    description = models.CharField(max_length=255)

class hangoutspots(models.Model):
    building = models.ForeignKey(building, on_delete=models.CASCADE)
    location = models.CharField(max_length=255)
    description = models.CharField(max_length=255)
    outlet = models.BooleanField(default=False)

class restroom(models.Model):
    building = models.ForeignKey(building, on_delete=models.CASCADE)
    location = models.CharField(max_length=255)
    accessibility = models.BooleanField(default=False)

class vendingmachine(models.Model):
    building = models.ForeignKey(building, on_delete=models.CASCADE)
    location = models.CharField(max_length=255)
    type = models.CharField(max_length=20)

class resource(models.Model):
    building = models.ForeignKey(building, on_delete=models.CASCADE)
    description = models.CharField(max_length=255)
    location = models.CharField(max_length=255)
    operating_hours = models.CharField(max_length=50)
    weblink = models.CharField(max_length=255)

class store(models.Model):
    building = models.ForeignKey(building, on_delete=models.CASCADE)
    description = models.CharField(max_length=255)
    location = models.CharField(max_length=255)
    operating_hours = models.CharField(max_length=50)

class dining_option(models.Model):
    building = models.ForeignKey(building, on_delete = models.CASCADE)
    description = models.CharField(max_length=255)
    location = models.CharField(max_length=255)
    operating_hours = models.CharField(max_length=255)
    weblink = models.CharField(max_length=255)
    menulink = models.CharField(max_length=255)

class lab(models.Model):
    building = models.ForeignKey(building, on_delete=models.CASCADE)
    location = models.CharField(max_length=255)
    type = models.CharField(max_length=20)
    department = models.CharField(max_length=255)

class elevator(models.Model):
    building = models.ForeignKey(building, on_delete = models.CASCADE)
    location = models.CharField(max_length=255)

class staircase(models.Model):
    building = models.ForeignKey(building, on_delete = models.CASCADE)
    location = models.CharField(max_length=255)

class special_feature(models.Model):
    building = models.ForeignKey(building, on_delete = models.CASCADE)
    location = models.CharField(max_length=255)
    description = models.CharField(max_length=255)
