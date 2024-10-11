<template>
  <div class="inbox">
    <h2 class="header">Inbox Emails</h2>
    <div v-if="emails.length === 0">
      <p>No emails in your inbox.</p>
    </div>
    <div v-else>
      <button @click="deleteSelectedEmails"><i class="fa fa-trash" aria-hidden="true"></i></button>
      <ul class="email-list">
        <li v-for="email in sortedEmails" :key="email.id" class="main-item">
          <div class="email-item" :class="{ 'unread': !email.read, 'read': email.read }">
            <div class="email-details">
              <div class="email-header">
                <div class="part_1">
                  <div class="checkbox">
                    <div><input type="checkbox" v-model="email.selected" /></div>&nbsp;&nbsp;&nbsp;&nbsp;
                    <div @click="toggleStarred(email)" class="star">
                      <i :class="['fa', email.important ? 'fa-star checked' : 'far fa-star']"></i>
                      <!-- <i v-if="email.important" class="fa fa-star checked"></i>
                      <i v-else class="far fa-star"></i> -->
                    </div>
                  </div>
                  <div class="email-sender">From: {{ email.senderId }}</div>
                </div>
                <div class="email-date">Date: {{ formatDate(email.sentDate) }}</div>
                <div class="part_2">
                  <div class="folders" @mouseover="showFolders(email)" @mouseleave="hideFolders(email)">
                    <i class="fas fa-folder-plus"></i>
                    <div v-show="email.showFolders" class="folder-dropdown">
                      <div v-for="folder in folders" :key="folder" @click="copyToFolder(email, folder)">
                        {{ folder }}
                      </div>
                    </div>
                  </div>
                  <div class="eye" @click="emailView(email)">
                    <i class="fa fa-eye" style="font-size:15px"></i>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      emails: [],
      folders: [],
    };
  },
  methods: {
    fetchEmails() {
      const emailId = localStorage.getItem('email');
      axios.get(`http://localhost:8080/users/get/${emailId}/inbox`)
        .then((response) => {
          console.log('Fetched emails:', response.data);
          this.emails = response.data.map(email => {
            // email.selected = false;
            // email.showFolders = false;
            return email;
          });
        })
        .catch((error) => {
          console.error('Error fetching emails:', error);
        });
    },
    emailView(email) {
      const emailId = email.id;
      axios.put(`http://localhost:8080/emails/markAsRead/${email.id}`)
        .then((response) => {
          console.log('Read status updated successfully', response.data);
          this.fetchEmails();
        })
        .catch((error) => {
          console.error('Error updating star status:', error);
        });
      this.$router.push({ name: 'EmailView', params: { id: emailId } });
    },
    formatDate(dateStr) {
      const options = { year: 'numeric', month: 'short', day: 'numeric', hour: 'numeric', minute: 'numeric', hour12: true };
      return new Date(dateStr).toLocaleString(undefined, options);
    },
    toggleStarred(email) {
      console.log('Toggling star for email:', email);
      const emailId = email.id;
      console.log('Email ID:', emailId);
      console.log('Email Object:', email);
      axios.put(`http://localhost:8080/emails/isImportant/${emailId}`)
        .then((response) => {
          console.log('Important status updated successfully', response.data);
          this.fetchEmails();
        })
        .catch((error) => {
          console.error('Error updating star status:', error);
        });
    },
    deleteSelectedEmails() {
      const selectedEmailIds = this.emails.filter((email) => email.selected).map((email) => email.id);
      if (selectedEmailIds.length === 0) {
        return; // No selected emails to delete
      }
      const email = localStorage.getItem('email');
      const deleteRequests = selectedEmailIds.map((id) => {
        return axios.put(`http://localhost:8080/users/moveToTrash/${email}/${id}`);
      });
      Promise.all(deleteRequests)
        .then((responses) => {
          // Handle successful deletion here
          console.log('Selected emails deleted successfully', responses);
          // Remove deleted emails from the local list
          this.emails = this.emails.filter((email) => !selectedEmailIds.includes(email.id));
        })
        .catch((error) => {
          console.error('Error deleting selected emails:', error);
        });
    },
  },
  computed: {
    sortedEmails() {
      return this.emails.slice().sort((a, b) => new Date(b.sentDate) - new Date(a.sentDate));
    },
  },
  showFolders(email) {
    email.showFolders = true;
  },
  hideFolders(email) {
    email.showFolders = false;
  },
  copyToFolder(email, folder) {
    // Implement the logic to copy the email to the selected folder here
    // For example, you can maintain an array of emails in each folder
    const folderIndex = this.folders.findIndex((f) => f.name === folder);
    if (folderIndex !== -1) {
      this.folders[folderIndex].emails.push(email);
    } else {
      this.folders.push({ name: folder, emails: [email] });
    }
    console.log(`Copy email to folder: ${folder}`);
    email.showFolders = false;
  },
  mounted() {
    this.fetchEmails();
  },
};
</script>

<style scoped>
.email-item {
  border: 1px solid #ccc;
  margin: 10px 0;
  padding: 10px;
  display: flex;
  flex-direction: row;
  align-items: center;
  border-radius: 10px;
}

.email-list {
  list-style: none;
  padding: 0;
}

.email-details {
  flex: 1;
}

.email-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.email-subject {
  margin-top: 5px;
  /* font-weight: bold; */
}

.email-date {
  font-size: 0.8rem;
  color: #777;
}
.email-item.unread {
  background-color: rgb(228, 220, 220);
  font-weight: bold;
}
.email-item.read {
  background-color: #ffffff;
  font-weight: normal;
}
.email-item.unread .email-sender {
  font-weight: bold;
}
.email-details {
  flex: 1;
}

.header {
  margin: 0;
  margin-bottom: 25px;
}

.part_1 {
  display: flex;
  justify-content: space-between;
}

.checkbox {
  margin-right: 20px;
  display: flex;
}

.part_2 {
  display: flex;
  justify-content: space-between;
}

.eye {
  margin-left: 20px;
  margin-right: 10px;
}

.checked {
  color: gold;
}

.star {
  display: flex;
  /* align-items: center; */
}

.folder-dropdown {
  position: absolute;
  background-color: white;
  border: 1px solid #ccc;
  padding: 5px;
  border-radius: 5px;
  top: 25px;
  left: 0;
  z-index: 1;
  display: none;
}

.folders:hover .folder-dropdown {
  display: block;
}
.main-item {
  background-color: #f5f5f5;
  border-radius: 10px;
}
.bold-text {
  font-weight: bold;
}
</style>
 