<template>
    <div class="inbox">
        <h2 class="header">Starred Emails</h2>
        <div v-if="emails.length === 0">
            <p>No emails in starred.</p>
        </div>
        <div v-else>
            <button @click="deleteSelectedEmails"><i class="fa fa-trash" aria-hidden="true"></i></button>
            <ul class="email-list">
                <li v-for="email in emails" :key="email.id" class="main-item">
                    <div class="email-item" :class="{ 'unread': !email.isRead, 'read': email.isRead }">
                        <div class="email-details">
                            <div class="email-header">
                                <div class="part_1">
                                    <div class="checkbox">
                                        <div><input type="checkbox" v-model="email.selected" /></div>&nbsp;&nbsp;&nbsp;&nbsp;
                                        <div @click="toggleStarred(email)" class="star">
                                            <i v-if="true" class="fa fa-star checked"></i>
                                            <i v-else class="far fa-star "></i>
                                        </div>
                                    </div>
                                    <div class="email-sender">From: {{ email.senderId }}</div>
                                </div>
                                <div class="email-date">Date: {{ formatDate(email.sentDate) }}</div>
                                <div class="part_2">
                                    <div class="folders" @mouseover="showFolders(email)" @mouseleave="hideFolders(email)">
                                        <i class="fas fa-folder-plus"></i>
                                        <div v-show="email.showFolders" class="folder-dropdown">
                                            <div v-for="folder in folders" :key="folder"
                                                @click="copyToFolder(email, folder)">
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
        };
    },
    methods: {
        fetchEmails() {
            const email = localStorage.getItem('email');
            axios.get(`http://localhost:8080/users/getStarred/${email}`)
                .then((response) => {
                    console.log('Fetched emails:', response.data);
                    this.emails = response.data.map(email => {
                        email.starred = false;
                        email.selected = false;
                        email.showFolders = false;
                        return email;
                    });
                })
                .catch((error) => {
                    console.error('Error fetching emails:', error);
                });
        },
        emailView(email) {
            const emailId = email.id;
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
            axios.put(`http://localhost:8080/emails/isNotImportant/${emailId}`)
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

.email-sender {
    /* font-weight: bold; */
}

.email-subject {
    margin-top: 5px;
    /* font-weight: bold; */
}

.email-date {
    font-size: 0.8rem;
    color: #777;
}

.email-item.read {
    font-weight: normal;
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
</style>