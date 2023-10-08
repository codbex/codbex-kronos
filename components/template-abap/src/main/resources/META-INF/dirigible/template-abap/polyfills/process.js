export const process = {
    stdout: {
        write: function() {
            console.log(...arguments);
        }
    }
}