export const tileModalStyle = {
    display: "grid", // Cambia a grid
    gridTemplateColumns: "repeat(auto-fill, minmax(100px, 1fr))", // Ajusta el número de columnas automáticamente
    gap: "10px", // Espaciado entre hexágonos
    padding: "10px",
    backgroundColor: "rgba(255, 255, 255, 0.9)",
    boxSizing: "border-box",
    width: "100%", // Ajusta el ancho al 100% del contenedor del modal
    overflowY: "auto", // Permite el desplazamiento vertical si es necesario
};

export const columnStyle = {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'center',
    height: '100vh',
    backgroundColor: '#f0f0f0',
};

export const baseHexStyle = {
    width: '7%',
    paddingTop: '7%',
    position: 'absolute',
    cursor: 'pointer',
    clipPath: 'polygon(25% 0%, 75% 0%, 100% 50%, 75% 100%, 25% 100%, 0% 50%)',
    opacity: '0.5',
    border: '2px solid black',
    boxSizing: 'border-box',
};

export const notificationStyle = {
    position: "bottom-center",
    autoClose: 4000,   
    closeOnClick: false, 
    draggable: false,   
    theme: "colored",
    style: {
        position: "bottom-center",
        fontSize: "18px",
        padding: "16px",
        backgroundColor: "white",  // Fondo blanco
        color: "orange",  // Texto naranja
        borderRadius: "8px",  // Bordes redondeados (opcional)
        boxShadow: "0 4px 6px rgba(0, 0, 0, 0.1)"  // Sombra sutil (opcional)
    }
};
