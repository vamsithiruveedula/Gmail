<template>
    <div>
        <!-- Button to create a new folder -->
        <button @click="showCreateFolderPopup" class="create-folder-button">Create New Folder</button>

        <!-- Display folders if they exist, or show a message if empty -->
        <div v-if="folders.length > 0" class="folder-list">
            <ul class="folder-list">
                <li v-for="(folder, index) in folders" :key="index" class="folder-item">{{ folder.folderName }}
                    <button @click="deleteFolder(index)" class="delete-button">Delete</button>
                    <ul class="email-list">
                        <li v-for="(email, emailIndex) in folder.emails" :key="emailIndex" class="email-item">
                            {{ email.subject }}
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
        <div v-else>
            <p class="no-folder-message">You have no important folders.</p>
        </div>

        <!-- Popup for creating a new folder -->
        <div v-if="showPopup" class="popup">
            <div>
                <h3 class="popup-header">Create Folder</h3>
                <input type="text" v-model="folderName" placeholder="Enter folder name" class="folder-input" />
                <button @click="createFolder" class="create-button">Create</button>
                <button @click="cancelCreateFolder" class="cancel-button">Cancel</button>
            </div>
        </div>
    </div>
</template>
  
<script>
import axios from 'axios';

export default {
    data() {
        return {
            folders: [],            // Array to store folders
            showPopup: false,       // Flag to control the popup visibility
            folderName: '',      // Store the new folder name
        };
    },
    methods: {
        // Show the create folder popup
        showCreateFolderPopup() {
            this.showPopup = true;
        },

        // Create a new folder
        async createFolder() {
            console.log('New folder name:', this.folderName);
            if (this.folderName) {
                try {
                    const folderData = {
                        folderName: this.folderName,
                        // You can add other data here if needed
                    };
                    console.log('New folder data:', folderData);
                    // Make an API request to create the folder
                    await axios.post(`http://localhost:8080/folders/save`, folderData);
                    // Once the folder is created, add it to the local array
                    this.folders.push({ folderName: this.folderName, emails: [] });
                    this.folderName = '';
                    this.showPopup = false;
                } catch (error) {
                    console.error('Error creating folder:', error);
                }
            }

        },

        // Cancel creating a new folder
        cancelCreateFolder() {
            this.folderName = '';
            this.showPopup = false;
        },
        // Delete a folder and its contents
        deleteFolder(index) {
            const folderToDelete = this.folders[index];
            axios.delete(`http://your-api-url/delete-folder/${folderToDelete.name}`)
                .then(() => {
                    // Once the folder is deleted on the server, remove it from the local array
                    this.folders.splice(index, 1);
                })
                .catch((error) => {
                    console.error('Error deleting folder:', error);
                });
        },

        // Copy an email to a folder
        copyToFolder(email, folderIndex) {
            this.folders[folderIndex].emails.push(email);
        },

        async fetchFolders() {
            try {
                const response = await axios.get(`http://localhost:8080/folders/getFolders`);
                this.folders = response.data;
            } catch (error) {
                console.error('Error fetching folders:', error);
            }
        },
    },
    mounted() {
        // Fetch folders from the server when the component is mounted
        this.fetchFolders();
    },
};
</script>
  
<style scoped>
.create-folder-button {
    background-color: #007bff;
    color: white;
    padding: 10px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    margin-top: 10px;
    margin-left: 10px;
}

/* .folder-list {
    margin-top: 20px;
    width: 99%;
}

.folder-item {
    background-color: #f0f0f0;
    padding: 5px;
    margin: 5px 0;
    border-radius: 5px;
} */
/* Style for the outer folder list */
.folder-list {
    list-style: none;
    padding: 0;
}

/* Style for each folder item */
.folder-item {
    display: flex;
    justify-content: space-between;
    background-color: #f0f0f0;
    padding: 5px;
    margin: 5px 0;
    border-radius: 5px;
    align-items: center;
}

/* Style for the delete button */
.delete-button {
    background-color: #ff0000;
    color: white;
    border: none;
    border-radius: 3px;
    cursor: pointer;
    padding: 3px 6px;
}

/* Style for the nested email list */
.email-list {
    list-style: none;
    padding: 0;
    margin-top: 5px;
}

/* Style for each email item */
.email-item {
    background-color: #ffffff;
    padding: 5px;
    margin: 3px 0;
    border-radius: 3px;
}

.no-folder-message {
    /* font-style: italic; */
    color: #888;
}

.popup {
    background-color: rgba(0, 0, 0, 0.8);
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
}

.popup div {
    background-color: white;
    padding: 20px;
    border-radius: 5px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
}

.popup-header {
    font-size: 20px;
    margin-bottom: 10px;
}

.folder-input {
    width: 90%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    margin-bottom: 10px;
}

.create-button {
    margin-top: 10px;
    margin-left: 20px;
    background-color: #007bff;
    color: white;
    padding: 10px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
}

.cancel-button {
    background-color: #ccc;
    color: rgba(71, 69, 69, 0.829);
    padding: 10px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    margin-left: 10px;
}
</style>
  