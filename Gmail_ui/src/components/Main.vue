<template>
  <div class="maincontainer">
    <div class="header">
      <div class="logo">
        <img class="logo_picture" src="./Gmail.png" alt="logo_Picture">
        <h1 class="h1">Gmail</h1>
      </div>
      <div class="search-bar"><input type="search" placeholder=" Search in emails"></div>
      <div class="profile">
        <div class="profilelogo">
          <img v-if="userData && userData.profilePicture !== null" :src="'data:image/jpeg;base64,' + userData.profilePicture" alt="Profile Image" class="picture" />
          <img v-else class="picture" src="./profile-icon.png" alt="Profile Picture">
        </div>
        <div class="username">
          <h3>{{ userData.firstName + ' ' + userData.lastName }}</h3>
        </div>
      </div>
    </div>
    <div class="container">
      <div class="navbar">
        <div class="links">
          <router-link to="/emailinbox" class="nav-link"><b>Inbox</b></router-link><br><br>
          <router-link to="/emailcompose" class="nav-link"><b>Compose</b></router-link><br><br>
          <router-link to="/emailsent" class="nav-link"><b>Sent</b></router-link><br><br>
          <router-link to="/starred" class="nav-link"><b>Starred</b></router-link><br><br>
          <router-link to="/trash" class="nav-link"><b>Trash</b></router-link><br><br>
          <router-link to="/allemail" class="nav-link"><b>All Mail</b></router-link><br><br>
          <router-link to="/important" class="nav-link"><b>Important</b></router-link><br><br>
          <router-link to="/profile" class="nav-link"><b>Profile</b></router-link>
        </div>
        <div class="nav-bottom">
          <div class="so">
            <span>
              <button class="btn" @click="signOut"><i class="fas fa-sign-out"></i> Sign Out</button>
            </span>
          </div>
          <div class="version">
            <span>
              1.0.0v
            </span>
          </div>
        </div>
      </div>
      <div class="data">
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>
    
<script>
import axios from 'axios';

export default {
  name: "Main",
  data() {
    return {
      userData: {
        profilePicture: null,
      },
    };
  },
  methods: {
    signOut() {
      const confirmed = window.confirm('Are you sure you want to sign out?');
      if (confirmed) {
        localStorage.removeItem('email');
        this.$router.push('/signin');
      }
    },
  },
  computed: {
  },
  mounted() {
    const email = localStorage.getItem('email');
    console.log('User Email:', email);
    axios.get(`http://localhost:8080/users/get/${email}`)
      .then((response) => {
        console.log(response.data);
        this.userData = response.data;
      })
      .catch((error) => {
        console.log(error);
      });
  },
};
</script>
    
<style scoped>
.header {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: space-between;
}

.logo {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: space-between;
  align-items: center;
}

.h1 {
  margin-left: 10px;
  color: rgb(82, 80, 80);
}

.profile {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: space-between;
  align-items: center;
}

.container {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  margin-left: 10px;
}

.navbar {
  width: 10%;
  display: flex;
  flex-direction: column;
  height: 86vh;
  justify-content: space-between;
}

.data {
  flex: 1;
  box-shadow: 5px 5px 1px 1px rgba(0, 0, 0, 0.5) inset;
  padding: 30px;
}

.profilelogo {
  margin-right: 20px;
  width: 50px;
  height: 50px;
  border-radius: 50%;


  /* width: 100px;
  height: 100px;
  background-color: #D3D9D9;
  border: 2px solid #D3D9D9;
  border-radius: 50%;
  position: relative;
  overflow: hidden;
  box-sizing: border-box;
  margin-right: 20px; */
}

/* .profilelogo::before { */
/* content: '';
  width: 32px;
  height: 32px;
  background-color: white;
  border-radius: 50%;
  position: absolute;
  left: calc(50% - 15px);
  top: 24px; */
/* } */
/* .profilelogo::after {
  content: '';
  width: 65px;
  height: 50px;
  background-color: white;
  border-radius: 50%;
  position: absolute;
  left: calc(50% - 32px);
  top: 63px;
} */
.username {
  text-transform: uppercase;
  margin-right: 10px;
}

.signout {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: space-between;
  margin-bottom: 25px;
}

.so {
  margin-bottom: 20px;
}

.nav-bottom {
  margin-bottom: 10px;
}

.nav-link.router-link-active {
  /* Style for active link */
  font-weight: bold;
  color: rgb(226, 93, 69);
  /* Add any other styles you want for active links */
}

.nav-link.router-link-exact-active {
  /* Style for exact active link */
  text-decoration: underline;
  /* Add any other styles you want for exact active links */
}

.nav-link {
  color: black;
  text-decoration: none;
  transition: color 0.3s ease;
  /* Add a smooth transition effect */
}

.btn {
  background-color: rgb(235, 53, 53);
  padding: 5px 10px;
  border-radius: 20px;
}

/* .btn {
  background-color: red;
  color: black;
  border: none;
  padding: 5px 10px;
} */
.picture {
  width: 50px;
  height: 50px;
  border: 1px solid black;
  border-radius: 50%;
}

.logo_picture {
  /* width: 50px;
  height: 30px; */
  width: auto;
  height: 20px;
}

.search-bar {
  display: flex;
  align-items: center;
}

input[type="search" i] {
  height: 30px;
  border-radius: 15px
}</style>
    