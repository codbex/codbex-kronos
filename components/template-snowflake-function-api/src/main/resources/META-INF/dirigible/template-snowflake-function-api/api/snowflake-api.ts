import { Controller, Post } from "sdk/http"
import { FunctionParamsDTO, FunctionResultDTO } from "./function-data-dto"

@Controller
class SnowflakeService {

    @Post("/")
    public sendData(dto: FunctionParamsDTO): FunctionResultDTO {
        const resultRows: [number, any][] = [];

        dto.data.forEach((rowData) => {
            const rowIndex: number = rowData[0];
            const params: any[] = rowData.slice(1);

            console.log(`Row index: [${rowIndex}], params: ${JSON.stringify(params)}`);
            const functionReturnValue = params.join("|");
            const resultRow: [number, any] = [rowIndex, functionReturnValue];

            resultRows.push(resultRow);
        });
        return {
            data: resultRows
        };

    }

}
