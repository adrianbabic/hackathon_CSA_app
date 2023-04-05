import React from "react";
import Navbar from "@/components/layout/Navbar";
import { AiFillDelete } from "react-icons/ai";
import { AiOutlinePlus } from "react-icons/ai";
import {AiFillEdit} from "react-icons/ai"
import { useState, useEffect } from "react";
import jwt_decode from "jwt-decode";
import {
  TextField,
  Box,
  Button,
  Typography,
  Modal,
  IconButton,
} from "@mui/material";

const System = () => {
  const dummySystem = [
    {
      id: 1,
      firstName: "Username 1",
    },
    {
      id: 2,
      firstName: "Username 2",
    },
    {
      id: 3,
      firstName: "Username 3",
    },
  ];

  const [users, setUsers] = useState(dummySystem);

  const deleteUser = (id) => {
    setUsers(users.filter((user) => user.id !== id));
  };

  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [role, setRole] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [open, setOpen] = useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);
  const [userId,setUserId] = useState(0);
  const [jwttoken,setJwttoken] = useState("");
  

  const handleEdit = (userId) => {
    

    const editUser = {
      id: userId,
      firstName: firstName,
      lastName: lastName,
      email: email,
      password: password,
      role: role,
      phoneNumber: phoneNumber,
      active: true,
    };

    const updatedUsers = users.map((user) =>
      user.id === userId ? editUser : user
    );

    setUsers(updatedUsers);
    setFirstName("");
    setLastName("");
    setPassword("");
    setEmail("");
    setRole("");
    setPhoneNumber("");
    setOpen(false);
  };

  const style = {
    position: "absolute",
    top: "50%",
    left: "50%",
    transform: "translate(-50%, -50%)",
    width: 400,
    bgcolor: "background.paper",
    border: "2px solid #000",
    boxShadow: 24,
    p: 4,
    color: "#000",
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    

    const newUser = {
      id: users.length + 1,
      firstName,
      lastName,
      password,
      email,
      role,
      phoneNumber,
      active:true

    };

    setUsers([...users, newUser]);
    setFirstName("");
    setLastName("");
    setPassword("");
    setEmail("");
    setRole("");
    setPhoneNumber("");
    handleEditClose();
    
  };

  //edit
  const [editOpen, setEditOpen] = useState(false);
  const handleEditOpen = () => setEditOpen(true);
  const handleEditClose = () => setEditOpen(false);

  const handleEditUser = (user) => {
    handleEditOpen();
    setUserId(user.id)
  }

  useEffect(() => {
    setJwttoken(jwt_decode(localStorage.getItem("token")).authorities);
  }, []);
  

  return (
    <>
      <Navbar />
      <div className="my-8 w-5/6 m-auto font-sans text-white">
        <h1 className="text-4xl font-bold font-mono text-center mb-10">
          SYSTEM
        </h1>
        {jwttoken == "ROLE_ADMIN" && (
        <button
          onClick={handleOpen}
          className="flex items-center justify-center p-4 w-2/6 m-auto h-16 border-solid border-2 rounded border-slate-500 text-white bold bg-slate-500 gap-2"
        >
          ADD USER <AiOutlinePlus className=""/>
        </button>
        )}
        <div className="my-12">
          {users.map((user) => (
            <div key={user.id}
              className={` my-8 p-4 rounded text-white bg-slate-800
              )}`}
            >
              <div className="flex items-center justify-between">
                <h2 className="text-3xl font-bold font-mono">
                  {user.firstName}{" "}
                </h2>
                {jwttoken == "ROLE_ADMIN" && (
                <div className="">
                <IconButton onClick={() => deleteUser(user.id)}>
                  <AiFillDelete
                    className="text-3xl text-white"
                    
                  />
                </IconButton>
                <IconButton >
                  <AiFillEdit
                    className="text-3xl text-white"
                    onClick={() => handleEditUser(user)}
                    
                  />
                </IconButton>
                </div>
                )}
              </div>
              <p>{user.description}</p>
            </div>
          ))}
        </div>

        <Modal
          open={open}
          onClose={handleClose}
          aria-labelledby="modal-modal-title"
          aria-describedby="modal-modal-description"
        >
          <Box sx={style}>
            <Typography id="modal-modal-title" variant="h6" component="h2">
              Add User
            </Typography>
            <Typography
              id="modal-modal-description"
              sx={{
                mt: 2,
                display: "flex",
                flexDirection: "column",
                gap: "10px",
              }}
              component="span"
            >
              <TextField
                id="outlined-basic"
                label="FirstName"
                variant="outlined"
                value={firstName}
                onChange={(e) => setFirstName(e.target.value)}
              />
              <br />
              <TextField
                id="outlined-basic"
                label="LastName"
                variant="outlined"
                value={lastName}
                onChange={(e) => setLastName(e.target.value)}
              />
              <br />

              <TextField
                id="outlined-basic"
                label="Password"
                variant="outlined"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
              <br />

              <TextField
                id="outlined-basic"
                label="Email"
                variant="outlined"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
              />
              <br />

              <TextField
                id="outlined-basic"
                label="Role"
                variant="outlined"
                value={role}
                onChange={(e) => setRole(e.target.value)}
              />
              <br />

              <TextField
                id="outlined-basic"
                label="PhoneNumber"
                variant="outlined"
                value={phoneNumber}
                onChange={(e) => setPhoneNumber(e.target.value)}
              />
              <br />

              
              

              <br />
              <Button variant="contained" onClick={handleSubmit}>
                Submit
              </Button>
            </Typography>
          </Box>
        </Modal>

        <Modal
          open={editOpen}
          onClose={handleEditClose}
          aria-labelledby="modal-modal-title"
          aria-describedby="modal-modal-description"
        >
          <Box sx={style}>
            <Typography id="modal-modal-title" variant="h6" component="h2">
              Edit User
            </Typography>
            <Typography
              id="modal-modal-description"
              sx={{
                mt: 2,
                display: "flex",
                flexDirection: "column",
                gap: "10px",
              }}
              component="span"
            >
              <TextField
                id="outlined-basic"
                label="FirstName"
                variant="outlined"
                value={firstName}
                onChange={(e) => setFirstName(e.target.value)}
              />
              <br />
              <TextField
                id="outlined-basic"
                label="LastName"
                variant="outlined"
                value={lastName}
                onChange={(e) => setLastName(e.target.value)}
              />
              <br />

              <TextField
                id="outlined-basic"
                label="Password"
                variant="outlined"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
              <br />

              <TextField
                id="outlined-basic"
                label="Email"
                variant="outlined"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
              />
              <br />

              <TextField
                id="outlined-basic"
                label="Role"
                variant="outlined"
                value={role}
                onChange={(e) => setRole(e.target.value)}
              />
              <br />

              <TextField
                id="outlined-basic"
                label="PhoneNumber"
                variant="outlined"
                value={phoneNumber}
                onChange={(e) => setPhoneNumber(e.target.value)}
              />
              <br />

              
              

              <br />
              <Button variant="contained" onClick={() => handleEdit(userId)}>
                Edit
              </Button>
            </Typography>
          </Box>
        </Modal>
      </div>
    </>
  );
};

export default System;
