<template>
  <div>
    <h1>
      {{ competition.name }}
    </h1>
    <h3>Available storages:</h3>
    <div v-for="storage in storages" :key="storage.id">
      <h4>
        {{ storage.name }} Responsible: {{ storage.storageManager.baseUserLogin }}
        <button @click="getStorageInfo(storage)">Show items</button>
      </h4>
    </div>

    <div v-if="activeStorage">
      <h3>Showing items for {{ activeStorage.name }}:</h3>
      <table v-for="item in equipment" :key="item.id">
        <tr>
          <td>{{ item.name }}</td>
          <td v-if="item.availability">
            <button @click="takeItem(item.id)">Take item</button>
          </td>
          <td v-else>
            <strong>This item was taken</strong>
          </td>
        </tr>
      </table>
      <div v-if="activeStorage.storageManager.id == manager">
        <h3>Storage records:</h3>
        <table v-for="item in storageRecords" :key="item.id">
          <tr>
            <td>{{ item.takenEquipment.name }}</td>
            <td>{{ item.creationDate }}</td>
            <td>{{ item.responsibleManager.baseUserLogin }}</td>
            <td>
              <button @click="returnItem(item.id)">Return item</button>
            </td>
          </tr>
        </table>
      </div>
    </div>
    <h3>Your open records:</h3>
    <table v-for="item in managerRecords" :key="item.id">
      <tr>
        <td>{{ item.takenEquipment.name }}</td>
        <td>{{ item.creationDate }}</td>
        <td>{{ item.originStorage.name }}</td>
      </tr>
    </table>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "ManageCompetitionStorages",
  props: ['manager', 'competition'],
  data: function () {
    return {
      storages: [],
      equipment: [],
      activeStorage: null,
      managerRecords: [],
      storageRecords: [],
    }
  },
  methods: {
    getStorages: function () {
      axios.get("/api/1/storage/" + this.competition.id + "/list", {}).then(response => {
        this.storages = response.data;
      });
    },
    changePage: function (page) {
      this.$root.$emit("onChangePage", page);
    },
    getStorageInfo: function (storage) {
      this.getStorageEquipment(storage);
      if (storage.storageManager.id == this.manager) {
        this.getStorageRecords(storage);
      }
    },
    getStorageEquipment: function (storage) {
      this.activeStorage = storage;
      axios.get("/api/1/equipment/" + storage.id + "/list", {}).then(response => {
        this.equipment = response.data;
        this.equipment.forEach(e => this.isEquipmentAvailable(e));
      });
    },
    isEquipmentAvailable: function (item) {
      axios.get("/api/1/equipment/" + item.id + "/available", {}).then((response) => {
        item.availability = response.data;
        this.$forceUpdate();
      });
    },
    takeItem: function (itemId) {
      axios.post("/api/1/equipment/take", {
        itemId: itemId,
        storageId: this.activeStorage.id,
        managerId: this.manager,
      }).then(() => {
        this.getStorageInfo(this.activeStorage)
        this.getManagerRecords(this.manager)
      });
    },
    getStorageRecords: function (storage) {
      axios.get("/api/1/records/storage/" + storage.id + "/list", {}).then((response) => {
        this.storageRecords = response.data;
      });
    },
    getManagerRecords: function (manager) {
      axios.get("/api/1/records/manager/" + manager + "/list", {}).then((response) => {
        this.managerRecords = response.data;
      });
    },
    returnItem: function (storageRecordId) {
      axios.post("/api/1/equipment/return", {
        storageRecordId: storageRecordId,
      }).then(this.updateInfo);
    },
    updateInfo: function () {
      this.getStorages();
      if(this.activeStorage) {
        this.getStorageInfo(this.activeStorage);
      }
      this.getManagerRecords(this.manager);
    },
  },
  beforeMount() {
    this.updateInfo();
  },
}
</script>

<style scoped>

</style>