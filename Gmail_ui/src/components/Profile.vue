<template>
  <div class="profile-data">
    <div class="header">
      <h2 class="header-name">User Profile Info</h2>
    </div>
    <div class="profile_form">
      <div class="profile-picture">
        <img v-if="user.profilePicture" :src="'data:image/jpeg;base64,' + user.profilePicture" alt="Profile Image" />
      </div>
      <div class="profile-details">
        <div class="form-group">
          <label for="email">Email: </label>
          <input type="email" id="email" v-model="user.email" disabled />
        </div>
        <div class="form-group">
          <label for="firstName">First Name: </label>
          <input type="text" id="firstName" v-model="user.firstName" />
        </div>
        <div class="form-group">
          <label for="lastName">Last Name: </label>
          <input type="text" id="lastName" v-model="user.lastName" />
        </div>
        <div class="form-group">
          <label for="mobileNo">Mobile No: </label>
          <input type="text" id="mobileNo" v-model="user.mobileNo" />
        </div>
        <div class="form-group">
          <label for="profilePicture">Profile Picture: </label>
          <input type="file" @change="handleProfilePicUpload" id="profilePicture" />
          <img v-if="profilePic" :src="profilePictureDataURL" alt="Profile" height="250" width="200" />
        </div>
        <div>
          <button @click="updateProfilePicture()">Save
            Picture</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <button @click="updateProfile()">Update Profile</button>
        </div>
        <div>
          <router-link to="/changepassword" class="nav-link"><b>Change your Password here</b></router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      user: {
        firstName: '',
        lastName: '',
        email: '',
        mobileNo: '',
        profilePicture: null,
      },
      profilePic: null,
    };
  },
  methods: {
    handleProfilePicUpload(event) {
      this.profilePic = event.target.files[0];
    },
    updateProfile() {
      const email = localStorage.getItem('email');
      const userData = {
        firstName: this.user.firstName,
        lastName: this.user.lastName,
        mobileNo: this.user.mobileNo,
      };
      // console.log('Request URL:', `http://localhost:8080/users/update/${email}`);
      // console.log('Request Data:', userData);
      axios.put(`http://localhost:8080/users/update/${email}`, userData)
        .then(response => {
          console.log('Profile updated successfully', response.data);
          this.user = response.data;
          this.fetchUserProfile();
        })
        .catch(error => {
          console.error('Error updating profile', error);
          console.log('Response data:', error.response.data);
        });
    },
    updateProfilePicture() {
      const email = localStorage.getItem('email');
      // Create a FormData object to send the file
      const formData = new FormData();
      formData.append('file', this.profilePic);
      // Make the PUT request to update the profile picture
      axios.put(`http://localhost:8080/users/changePicture/${email}`, formData, {
        headers: {
          'Content-Type': 'multipart/form-data', // Set the content type to multipart/form-data
        },
      })
        .then(response => {
          console.log('Profile picture updated successfully', response.data);
          this.user = response.data;
        })
        .catch(error => {
          console.error('Error updating profile picture', error);
          console.log('Response data:', error.response.data);
        });
    },
    fetchUserProfile() {
      const email = localStorage.getItem('email');
      axios.get(`http://localhost:8080/users/get/${email}`)
        .then(response => {
          console.log('Fetched user profile:', response.data);
          // Update the user object with the fetched data
          this.user = response.data;
        })
        .catch(error => {
          console.error('Error fetching user profile', error);
        });
    },
  },
  mounted() {
    this.fetchUserProfile();
  },
};
</script>

<style scoped>
.profile-data {
  border-radius: 5px;
}

.form-group {
  margin-bottom: 20px;
}

.header-name {
  margin-top: 0;
  margin-bottom: 25px;
  font-size: 24px;
}

.profile_form {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
}

.profile-picture {
  flex: 1;
  text-align: center;
}

.profile-picture img {
  max-width: 100%;
  max-height: 250px;
  border-radius: 5px;
}

.profile-details {
  flex: 2;
}

.profile-details div {
  margin-bottom: 15px;
}

.profile-details label {
  font-weight: bold;
}

.profile-details input[type="text"],
.profile-details input[type="email"] {
  width: 25%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 14px;
}

.profile-details button {
  background-color: #007BFF;
  color: #fff;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.profile-details button:hover {
  background-color: #0056b3;
}

.nav-link {
  text-decoration: none;
  color: #326e2c;
  font-weight: bold;
  margin-top: 10px;
  display: inline-block;
}

.nav-link:hover {
  text-decoration: underline;
}
</style>