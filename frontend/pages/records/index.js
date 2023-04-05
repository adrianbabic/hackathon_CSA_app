import React from "react";
import Navbar from "@/components/layout/Navbar";
import { AiFillDelete } from "react-icons/ai";
import { AiOutlinePlus } from "react-icons/ai";
import { AiFillEye } from "react-icons/ai";
import { useState, useEffect } from "react";
import axios from "axios";
import jwt_decode from "jwt-decode";
import {
  TextField,
  Box,
  Button,
  Typography,
  Modal,
  IconButton,
} from "@mui/material";

const Records = () => {
  const dummyRecords = [
    {
      id: 1,
      name: "Low disk space",
      source: "github.com",
      severity: "High",
      potentialImpact: 0.85,
      time: "18/3/2023",
    },
    {
      id: 2,
      name: "SQL Injection Vulnerability",
      source: "OWASP",
      severity: "Critical",
      potentialImpact: 0.95,
      time: "17/3/2023",
    },

    {
      id: 3,
      name: "Cross-Site Scripting (XSS) Attack",
      source: "NIST",
      severity: "High",
      potentialImpact: 0.8,
      time: "16/3/2023",
    },

    {
      id: 4,
      name: "Broken Authentication and Session Management",
      source: "SANS Institute",
      severity: "Medium",
      potentialImpact: 0.7,
      time: "15/3/2023",
    },

    {
      id: 5,
      name: "Denial-of-Service (DoS) Attack",
      source: "CERT/CC",
      severity: "High",
      potentialImpact: 0.9,
      time: "14/3/2023",
    },
  ];

  const [records, setRecords] = useState([]);

  //Modal
  const [open, setOpen] = useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);
  const [name,setName] = useState("");
  const [source,setSource] = useState("");
  const [severity,setSeverity] = useState("");
  const [potentialImpact,setPotentialImpact] = useState("");
  const [index,setIndex] = useState(-1);
  const [jwttoken,setJwttoken] = useState("");
  const [file, setFile] = useState(null);


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
     const date = new Date();

     let day = date.getDate();
     let month = date.getMonth() + 1;
     let year = date.getFullYear();

     const newRecord = {
       id: records.length + 1,
       name: name,
       source: source,
       severity: severity,
       potentialImpact: potentialImpact,
       time: `${day}/${month}/${year}`,
     };
     axios
    .post("https://evil-evaluators-spring-evil-evaluators-3.azuremicroservices.io/records/",newRecord)
       .then((response) => displayOutput(response))
       .catch((err) => console.log(err));
     setRecords([...records, newRecord]);
     setName("");
     setSource("");
     setSeverity("");
     setPotentialImpact("");
     handleClose();
   };

  const deleteRecord = (id) => {
    axios.delete("https://evil-evaluators-spring-evil-evaluators-3.azuremicroservices.io/records/" + id,{headers: {
      Authorization: `Bearer ${localStorage.getItem('token')}`
  }}).then();
  };

  const fetchRecordsData = () => {
    axios.get("https://evil-evaluators-spring-evil-evaluators-3.azuremicroservices.io/records",{headers: {
      Authorization: `Bearer ${localStorage.getItem('token')}`
  }}).then((response) => {
      setRecords(response.data);
    });
  };

  useEffect(() => {
    setJwttoken(jwt_decode(localStorage.getItem("token")).authorities);
    fetchRecordsData();
  }, [records]);

  const dangerLevel = (level) => {
    if (level > 0 && level <= 33) {
      return "bg-sky-500";
    } else if (level > 33 && level <= 66) {
      return "bg-yellow-500";
    } else {
      return "bg-red-500";
    }
  };

  const handleFileInputChange = (e) => {
    setFile(e.target.files[0]);
  };

  const handleFormSubmit = (e) => {
    e.preventDefault();
    const formData = new FormData();
    formData.append('file', file);
    axios.post("https://evil-evaluators-spring-evil-evaluators-3.azuremicroservices.io/records/file", formData, {headers: {
      'Content-Type': 'multipart/form-data'
    }}).then(() => {
      alert("File uploaded successfully");
      fetchRecordsData();
    });
  };

  return (
    <>
      <Navbar />
      <div className="my-8 w-5/6 m-auto font-sans">
        <h1 className="text-4xl font-bold font-mono text-center mb-10">
          RECORDS
        </h1>
        {jwttoken === "ROLE_ADMIN" && (
        <button
          onClick={handleOpen}
          className="flex items-center justify-center p-4 w-2/6 m-auto h-16 border-solid border-2 rounded border-blue-900 text-black bg-blue-900 gap-2"
        >
          ADD RECORD <AiOutlinePlus />
        </button>
        )}
        {jwttoken === "ROLE_ADMIN" && (
          <div className="flex flex-col items-center justify-center px-6 py-8 mx-auto mt-8 lg:py-0 p-6 space-y-4 md:space-y-6 sm:p-8 w-full bg-white rounded-lg shadow dark:border md:mt-0 sm:max-w-md xl:p-0 dark:bg-gray-800 dark:border-gray-700 center">
          <form className="space-y-4 md:space-y-6" onSubmit={handleFormSubmit}>
            <input className="block mb-2 text-sm font-medium text-gray-900 dark:text-white" type="file" onChange={handleFileInputChange} />
            <button className="w-full text-white bg-primary-600 hover:bg-primary-700 focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-primary-600 dark:hover:bg-primary-700 dark:focus:ring-primary-800" type="submit">Upload</button>
          </form>
        </div>
        )}
        <div className="my-12">
          {records.data &&
            records.data.map((record) => (
              <div
                key={record.id}
                className={` my-8 p-4 rounded text-black ${dangerLevel(
                  record.potentialImpact * 100
                )}`}
              >
                <div className="flex items-center justify-between">
                  <div className="text-3xl">
                    <IconButton
                      onClick={() =>
                        index === record.id ? -1 : setIndex(record.id)
                      }
                    >
                      <AiFillEye />
                    </IconButton>
                  </div>
                  <h2 className="text-3xl w-3/6 font-bold font-mono">
                    {"Record "}
                    {record.id}
                  </h2>
                  <p>{record.timestamp}</p>
                  {jwttoken === "ROLE_ADMIN" && (
                  <IconButton onClick={() => deleteRecord(record.id)}>
                    <AiFillDelete className="text-3xl" />
                  </IconButton>
                  )}
                </div>
                {index === record.id && (
                  <div>
                    {record.threats.map((threat) => (
                      <div className="border-b-2 py-2">
                        <div className="flex my-4 ">
                          <h3 className="flex-1 text-center">Name:</h3>
                          <h3 className="flex-1 text-center">Source:</h3>
                          <h3 className="flex-1 text-center">Severity:</h3>
                        </div>
                        <div className="flex">
                          <p className="flex-1 text-center">{threat.name}</p>
                          <p className="flex-1 text-center">{threat.source}</p>
                          <p className="flex-1 text-center">
                            {threat.severity}
                          </p>
                        </div>
                      </div>
                    ))}
                  </div>
                )}
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
              Add Record
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
                label="Name"
                variant="outlined"
                value={name}
                onChange={(e) => setName(e.target.value)}
              />
              <br />
              <TextField
                id="outlined-basic"
                label="Source"
                variant="outlined"
                value={source}
                onChange={(e) => setSource(e.target.value)}
              />
              <br />
              <TextField
                id="outlined-basic"
                label="Severity"
                variant="outlined"
                value={severity}
                onChange={(e) => setSeverity(e.target.value)}
              />
              <br />
              <TextField
                id="outlined-basic"
                label="PotentialImpact"
                variant="outlined"
                value={potentialImpact}
                onChange={(e) => setPotentialImpact(e.target.value)}
                type="number"
                InputProps={{ inputProps: { min: 0, max: 1.0, step: 0.01 } }}
              />

              <br />
              <Button variant="contained" onClick={handleSubmit}>
                Submit
              </Button>
            </Typography>
          </Box>
        </Modal>
      </div>
    </>
  );
};

export default Records;
