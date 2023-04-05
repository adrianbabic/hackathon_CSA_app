import React from "react";
import Navbar from "@/components/layout/Navbar";
import { AiFillDelete } from "react-icons/ai";
import { AiOutlinePlus } from "react-icons/ai";
import { useState, useEffect } from "react";
import {
  TextField,
  Box,
  Button,
  Typography,
  Modal,
  IconButton,
  FormControlLabel,
  Checkbox,
  FormControl,
  InputLabel,
  Select,
  MenuItem
} from "@mui/material";
import axios from "axios";
import jwt_decode from "jwt-decode";

const Alerts = () => {
  const dummyAlerts = [
    {
      id: 1,
      message: "Low disk space",
      description: "Your hard drive is almost full. Please free up some space.",
      dangerLevel: 30,
    },
    {
      id: 2,
      message: "Server down",
      description:
        "The server is currently unavailable. Our engineers are working to fix the issue.",
      dangerLevel: 90,
    },
    {
      id: 3,
      message: "Unauthorized access",
      description:
        "Someone tried to access your account without your permission. Please change your password immediately.",
      dangerLevel: 60,
    },
  ];

  const [alerts, setAlerts] = useState(dummyAlerts);
  const [jwttoken,setJwttoken] = useState("");

  const dangerClass = (dangerLevel) => {
    if (dangerLevel === "severity") {
      return "bg-red-200";
    } else if (dangerLevel === "source") {
      return "bg-red-300";
    } else {
      return "bg-red-500";
    }
  };
  //Modal
  const [open, setOpen] = useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);
  const [upload, setUpload] = useState(false);
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [data, setData] = useState("");
  const [field, setField] = useState("");
  const [minor, setMinor] = useState(false);

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

    const newAlert = {
      id: alerts.length + 1,
      name: name,
      description: description,
      data:data,
      field:field,
      minor:minor,

    };

    // setAlerts([...alerts, newAlert]);
    axios
      .post("https://evil-evaluators-spring-evil-evaluators-3.azuremicroservices.io/alert/", newAlert)
      .then((response) => {
        console.log(response.data);
      })
      .catch((err) => console.log(err));
    setName("");
    setDescription("");
    setData("");
    setField("");
    setMinor("");

    handleClose();
  };

  const deleteAlert = (id) => {
    axios.delete("https://evil-evaluators-spring-evil-evaluators-3.azuremicroservices.io/alert/?id=" + id).then();
  };

  const fetchAlertsData = () => {
    axios.get("https://evil-evaluators-spring-evil-evaluators-3.azuremicroservices.io/alert").then((response) => {
      setAlerts(response.data);
    });
  };
  console.log(alerts.data);

  useEffect(() => {
    setJwttoken(jwt_decode(localStorage.getItem("token")).authorities);
    fetchAlertsData();
  }, [alerts]);

  return (
    <>
      <Navbar />
      <div className="my-8 w-5/6 m-auto font-sans">
        <h1 className="text-4xl font-bold font-mono text-center mb-10">
          ALERTS
        </h1>
        {jwttoken === "ROLE_ADMIN" && (
        <button
          onClick={handleOpen}
          className="flex items-center justify-center p-4 w-2/6 m-auto h-16 border-solid border-2 rounded border-red-100 text-black bg-red-100 gap-2"
        >
          ADD ALERT <AiOutlinePlus />
        </button>
        )}
        <div className="my-12">
          {alerts.data &&
            alerts.data.map((alert) => (
              <div
                key={alert.id}
                className={` my-8 p-4 rounded text-black ${dangerClass(
                  alert.field
                )}`}
              >
                <div className="flex items-center justify-between">
                  <h2 className="text-3xl font-bold font-mono">
                    {alert.name}{" "}
                  </h2>
                  {jwttoken === "ROLE_ADMIN" && (
                  <IconButton onClick={() => deleteAlert(alert.id)}>
                    <AiFillDelete className="text-3xl" />
                  </IconButton>
                  )}
                </div>
                <p>{alert.description}</p>
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
              Add Alert
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
                label="Description"
                variant="outlined"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
              />
              <br />
              <TextField
                id="outlined-basic"
                label="Data"
                variant="outlined"
                value={data}
                onChange={(e) => setData(e.target.value)}
              />
              <br />
              <FormControl fullWidth>
                <InputLabel id="demo-simple-select-label">Field</InputLabel>
                <Select
                  labelId="demo-simple-select-label"
                  id="demo-simple-select"
                  value={field}
                  label="Field"
                  onChange={(e) => setField(e.target.value)}
                >
                  <MenuItem value={'name'}>Name</MenuItem>
                  <MenuItem value={'security'}>Security</MenuItem>
                  <MenuItem value={'source'}>Source</MenuItem>
                  <MenuItem value={'potentialImpact'}>PotentialImpact</MenuItem>
                  <MenuItem value={'deviceType'}>DeviceType</MenuItem>
                </Select>
              </FormControl>
              <br />
              <FormControlLabel
                control={
                  <Checkbox defaultChecked onChange={() => setMinor(!minor)} />
                }
                label="Minor"
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

export default Alerts;
